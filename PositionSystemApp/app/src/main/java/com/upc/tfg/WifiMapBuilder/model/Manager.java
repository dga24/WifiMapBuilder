package com.upc.tfg.WifiMapBuilder.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.upc.tfg.WifiMapBuilder.Utils;
import com.upc.tfg.WifiMapBuilder.dataBase.AppRoomDatabase;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.EstacionBase;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestras;
import com.upc.tfg.WifiMapBuilder.model.quality.QualityCalculator;
import com.upc.tfg.WifiMapBuilder.model.quality.QualityCalculatorByRSSThreshold;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {



//    private CoordenadaDao mCoordenadaDao;
//    private EstacionBaseDao mEstacionBaseDao;
//    private MapaDao mMapaDao;
//    private MedidaDao mMedidaDao;
//    private MuestraDao mMuestraDao;
//    private MuestrasDao mMuestrasDao;

    Utils utils;
    QualityCalculatorByRSSThreshold qualityCalculatorByRSSThreshold;

    MyAsyncTask myAsyncTask;
    Context context;

    public Manager(Context context) {
        this.context = context;
    }

    public void getMuestrasByPosition(Position position){

    }


    // 1. Obtener número de muestras
    // 2. Obtener los distintos BSIDs
    // 3. Obtener los datos de cada BSID
    // 3. Para cada BSID obtener lista de muestras
    // 4. Para cada lista de muestras generar un BSSignalStatistics a partir del tipo
    // 5. Incluir el BSSignalStatics en la lista de salida.



    final ArrayList<EstacionBase> bsss = new ArrayList<>();
    int medidai=0;


    public void getBSinMedidaId(final int medidaID, final AppRoomDatabase db, final List<EstacionBase> bsss, OnFinishListener listener) {
        MyAsyncTask myAsynctask = new MyAsyncTask(context);
        final ArrayList<Integer> bsids = new ArrayList<>();
        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 50;
            }
            @Override
            public int run() {
                List<Muestras> lstmuestras = db.muestrasDao().getMuestrasByMedidaId(medidaID);
                bsids.add(db.muestraDao().getDifferentBsid(lstmuestras.get(1).getMuestrasid()));
                return 0;
            }
            @Override
            public String description() {
                return null;
            }
        });
        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 50;
            }
            @Override
            public int run() {
                for (Integer id : bsids) {
                    bsss.add(db.estacionBaseDao().getEstacionBase(id));
                }
                return 0;
            }
            @Override
            public String description() {
                return null;
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsynctask.execute();
    }

    public void getMuestrasByMedidaId(final int medidaID, final List<APSignalStatistics> apSignalStatistics,
                                      final QualityCalculator qualityCalculator, final AppRoomDatabase db, OnFinishListener listener) {

        MyAsyncTask myAsyncTask = new MyAsyncTask(context);

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                Medida medida = db.medidaDao().getMedidaById(medidaID);
                int numMuestras = medida.getNumMuestras();
                int numRep = medida.getRepeticiones();

                //listaMuestras en esa medida
                List<Muestras> lstmuestras = db.muestrasDao().getMuestrasByMedidaId(medidaID);

                List<Muestra> lstmuestra = new ArrayList<>();
                for (Muestras ms:
                        lstmuestras) {
                    lstmuestra.addAll(db.muestraDao().getListaMuestras(ms.getMuestrasid()));
                }

                List<EstacionBase> lstEstacionBase = new ArrayList<>();
                EstacionBase eb;
                boolean contains = false;
                boolean saved = false;
                for (Muestra m :
                        lstmuestra) {
                    eb = db.estacionBaseDao().getEstacionBase(m.getBsid());
                    contains=false;
                    for (EstacionBase ebaux :
                            lstEstacionBase) {
                        if(ebaux.getBsid()==eb.getBsid()){
                            contains=true;
                            break;
                        }
                    }
                    if(!contains){
                        lstEstacionBase.add(eb);
                    }

                }

                double mean;
                double var;
                double quality;
                int id;

                List<Muestra> lstm = new ArrayList<>();
                utils = new Utils();

                for (EstacionBase estacionBase :
                        lstEstacionBase) {
                    id = estacionBase.getBsid();
                    for (Muestra ma :
                            lstmuestra) {
                        if (ma.getBsid() == id){
                            lstm.add(ma);
                        }
                    }
                    mean = utils.calcularMedia(lstm);
                    var = utils.calcularVarianza(lstm);
                    quality = utils.computeQuality(qualityCalculator,lstm, numMuestras*numRep);
                    apSignalStatistics.add(new APSignalStatistics(estacionBase.getSsid(),estacionBase.getMac(),mean,var,quality));
                    lstm.clear();
                }
                return 0;
            }

            @Override
            public String description() {
                return "saving map...";
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();

    }

    public void getAllMapas(final AppRoomDatabase db,final List<Mapa> mapas, OnFinishListener listener){
        MyAsyncTask myAsyncTask = new MyAsyncTask(context);

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                mapas.addAll(db.mapadao().getAllMapas());
                Log.d("DB", "Mapas obtenidos");
                return 0;
            }

            @Override
            public String description() {
                return "saving map...";
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();
    }

    public void createMap(final Mapa mapa, final AppRoomDatabase db, OnFinishListener listener){

        MyAsyncTask myAsyncTask = new MyAsyncTask(context);

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                long mapaid = db.mapadao().createMapa(mapa);
                Log.d("DB", "Mapa creado");
                return 0;
            }

            @Override
            public String description() {
                return "saving map...";
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();
    }

    public void coverageView(final int mapaid, final List<PointCoverage> pointsCoverages, final AppRoomDatabase db, OnFinishListener listener){

        MyAsyncTask myAsyncTask = new MyAsyncTask(context);
        final List<Medida> medidas = new ArrayList<>();

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                medidas.addAll(db.medidaDao().getMedidasByMapaId(mapaid));
                for (Medida me:
                        medidas) {
                    Coordenada coor= db.coordenadaDao().getCoordenada(me.getPosicionid());
                    int nAps = getNumberApsinMedida(me,db);
                    pointsCoverages.add(new PointCoverage(coor, nAps));
                }
                Log.d("DB", "Mapa creado");
                return 0;
            }

            @Override
            public String description() {
                return "saving map...";
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();
    }

    public void setCoorOrigen(final Mapa mapa, final Coordenada coordenada, final AppRoomDatabase db, OnFinishListener listener){
        MyAsyncTask myAsyncTask = new MyAsyncTask(context);

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                coordenada.setMapaid(mapa.getMapaid());
                long coorid = db.coordenadaDao().insertCoordenada(coordenada);
                Log.d("DB", "Coordenada creada");
                db.mapadao().updateOrigenId(mapa.getMapaid(), (int) coorid);
                mapa.setCoordenadaid((int) coorid);
                return 0;
            }

            @Override
            public String description() {
                return "saving CoordenadaOrigen...";
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();
    }

    public int getNumberApsinMedida(Medida medida,AppRoomDatabase db){

        int numMuestras = medida.getNumMuestras();

        //listaMuestras en esa medida
        List<Muestras> lstmuestras = db.muestrasDao().getMuestrasByMedidaId(medida.getMedidaid());

        List<Muestra> lstmuestra = new ArrayList<>();
        for (Muestras ms:
                lstmuestras) {
            lstmuestra.addAll(db.muestraDao().getListaMuestras(ms.getMuestrasid()));
        }
        List<EstacionBase> lstEstacionBase = new ArrayList<>();
        EstacionBase eb;
        boolean contains = false;
        boolean saved = false;
        for (Muestra m :
                lstmuestra) {
            eb = db.estacionBaseDao().getEstacionBase(m.getBsid());
            contains=false;
            for (EstacionBase ebaux :
                    lstEstacionBase) {
                if(ebaux.getBsid()==eb.getBsid()){
                    contains=true;
                    break;
                }
            }
            if(!contains){
                lstEstacionBase.add(eb);
            }
        }
        return lstEstacionBase.size();
    }

    public void createListBs(final List<EstacionBase> bs, final AppRoomDatabase db, OnFinishListener listener){
        MyAsyncTask myAsyncTask = new MyAsyncTask(context);

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int run() {
                List<Integer> bsids= createBaseStations(bs,db);

                return 0;
            }

            @Override
            public String description() {
                return null;
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();
    }

    public void saveCapture(final List<MuestraCapturada> lstMuestraCap, final Coordenada coordenada, final Medida medida, final AppRoomDatabase db,
                            OnFinishListener listener){
        MyAsyncTask myAsyncTask = new MyAsyncTask(context);
        int medidaid;

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 50;
            }

            @Override
            public int run() {
                int coorId = (int) db.coordenadaDao().insertCoordenada(coordenada);
                medida.setFechaFin(new Date().toString());
                medida.setPosicionid(coorId);
                int medidaid = (int) insertMedida(medida,db);
                medida.setMedidaid(medidaid);
                Medida medidaAux = db.medidaDao().getMedidaById(medidaid);
                createMuestras(lstMuestraCap, medidaAux, db);
                return 0;
            }

            @Override
            public String description() {
                return null;
            }
        });
        myAsyncTask.addOnFinishListener(listener);
        myAsyncTask.execute();

    }

    public Mapa getMapa(final int mapaid, final AppRoomDatabase db){
        return db.mapadao().getMapa(mapaid);
    }

    public long getLastMapid(final AppRoomDatabase db){
        return db.mapadao().getLastMapid();
    }

    public long insertMedida(Medida medida, AppRoomDatabase db){
        return db.medidaDao().createMedida(medida);
    }

    public Medida createMedida(final Coordenada coordenada, int mapaid, int ang, Parameters parameters, AppRoomDatabase db){
        int coordenadaid = (int) db.coordenadaDao().insertCoordenada(coordenada);

        Medida medida = new Medida(coordenadaid,ang,mapaid,new Date().toString(),new Date().toString(),
                                parameters.getPeriod(),parameters.getRep(),parameters.getTemp(),parameters.getNumSample());

        int medidaid = (int) db.medidaDao().createMedida(medida);
        medida = db.medidaDao().getMedidaById(medidaid);
        return medida;
    }

    public List<Integer> createBaseStations(List<EstacionBase> bss, AppRoomDatabase db){
        List<Integer> bsids =null;
        for (EstacionBase bs:
                bss) {
            bsids.add((int) db.estacionBaseDao().createEstacionBase(bs));
        }
        return bsids;
    }

    public int[] createMuestras(List<MuestraCapturada> lstMuestraCap, Medida medida, AppRoomDatabase db){
        int[] muestrasids=null;

        List<Integer> msids = new ArrayList<>();
        int i=0;
        while(i<medida.getRepeticiones()){
            Muestras mm = new Muestras(medida.getMedidaid(),i);
            msids.add((int) db.muestrasDao().createMuestras(mm));
            i++;
        }

        for (MuestraCapturada mc :
                lstMuestraCap) {
                int bsid = createBS(mc,db);     // Comprovar si hay bs, si no hay crear, si hay asignarle la id
                //obtenermuestrasid para asignarselo a muestra
                int muestrasId = (int) db.muestrasDao().getMuestrasIdByMedidaIdRep( medida.getMedidaid(), mc.getRepeticion());
                Muestra m = new Muestra(muestrasId, mc.getValor(),mc.getNummuestra(),bsid);
                int mid = (int) db.muestraDao().createMuestra(m);
                System.out.print(mid);
        }

        return muestrasids;
    }

    public int createBS(MuestraCapturada mc, AppRoomDatabase db){
        int bsid = 0;
        if(1==db.estacionBaseDao().existBs(mc.getBssid())){
            bsid=db.estacionBaseDao().getBSIDByMac(mc.getBssid());
        }else{
            bsid = (int) db.estacionBaseDao().createEstacionBase(new EstacionBase(mc.getSsid(),mc.getBssid(),"WIFI"));
        }
        return bsid;
    }



}
