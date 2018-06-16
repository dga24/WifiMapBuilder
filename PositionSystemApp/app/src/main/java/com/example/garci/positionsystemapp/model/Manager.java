package com.example.garci.positionsystemapp.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.garci.positionsystemapp.Utils;
import com.example.garci.positionsystemapp.dataBase.AppRoomDatabase;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestras;
import com.example.garci.positionsystemapp.model.quality.QualityCalculator;
import com.example.garci.positionsystemapp.model.quality.QualityCalculatorByRSSThreshold;

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
        //final ArrayList<EstacionBase> bsss = new ArrayList<>();

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



    public List<BSSignalStatistics> getMuestrasByMedida(int medidaID, QualityCalculator qualityCalculator,AppRoomDatabase db) {

        AsyncTask myAsynctask;


        Medida medida = db.medidaDao().getMedidaById(medidaID);
        int numMuestras = medida.getNumMuestras();

        //listaMuestras en esa medida
        List<Muestras> lstmuestras = db.muestrasDao().getMuestrasByMedidaId(medidaID);

        List<Integer> bsids = null; //bsids diferentes


        //Con qe salga en una repeticion ya esta? ha de salir en todas?
//        for (Muestras m :
//                lstmuestras) {
//            bsids.add(db.muestraDao().getDifferentBsid(m.getMuestrasid()));
//        }
        //Devuelve los diferentes bsids de la 1a 1a repeticion solo
        bsids.add(db.muestraDao().getDifferentBsid(lstmuestras.get(1).getMuestrasid()));

        List<EstacionBase> bsss = null; //lista estaciones base
        for (int id :
                bsids) {
            bsss.add(db.estacionBaseDao().getEstacionBase(id));
        }

        List<Muestra> lstmuestra = null;
        List<BSSignalStatistics> bsSignalStatistics = null;
        double mean;
        double var;
        double quality;
        for (int i:
             bsids) {
            for (Muestras mm :  //de cada repeticion obtengo la lista de muestra
                    lstmuestras) {
                lstmuestra.addAll((db.muestraDao().getLstMuestraByBsidAndMuestrasId(i, mm.getMuestrasid())));
            }
            mean = utils.calcularMedia(lstmuestra);
            var = utils.calcularVarianza(lstmuestra);
            quality = utils.computeQuality(qualityCalculatorByRSSThreshold,lstmuestra, numMuestras);
            bsSignalStatistics.add(new APSignalStatistics(
                    String.valueOf(i),
                    db.estacionBaseDao().getEstacionBase(i).getMac(),
                    0,0,0));
            lstmuestra = null;

        }
        return bsSignalStatistics;
    }

    public void createMap(final Mapa mapa, final Position origen, final AppRoomDatabase db){

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
                Coordenada coor = null;
                coor.setMapaid((int) mapaid);
                coor.setX(origen.getX());
                coor.setY(origen.getY());
                coor.setZ(origen.getZ());
                coor.setPixelx(origen.getPixelX());
                coor.setPixely(origen.getPixelY());
                long coorid = db.coordenadaDao().insertCoordenada(coor);
                db.mapadao().updateOrigenId((int)mapaid,(int) coorid);
                return 0;
            }

            @Override
            public String description() {
                return "saving map...";
            }
        });
    }

    public void createListBs(final List<EstacionBase> bs, final AppRoomDatabase db){
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
    }


    public void saveCapture(final List<MuestraCapturada> lstMuestraCap, final Parameters parameters, final Coordenada coordenada, final int angle, OnFinishListener listener, final AppRoomDatabase db){
        MyAsyncTask myAsyncTask = new MyAsyncTask(context);
        int medidaid;

        myAsyncTask.addTask(new ITask() {
            @Override
            public int weight() {
                return 25;
            }

            @Override
            public int run() {
                Medida medida = createMedida(coordenada, angle, parameters, db);
                createMuestras(lstMuestraCap, medida, db);
                return 0;
            }

            @Override
            public String description() {
                return null;
            }
        });

    }

    public Medida createMedida(final Coordenada coordenada, int ang, Parameters parameters, AppRoomDatabase db){
        int coordenadaid = (int) db.coordenadaDao().insertCoordenada(coordenada);
        Medida medida = null;
        medida.setPosicionid(coordenadaid);
        medida.setAngulo(ang);
        Date date = new Date();
        medida.setFechaInicio(date.toString());
        medida.setFechaFin(date.toString());
        medida.setPeriodo(parameters.getPeriod());
        medida.setRepeticiones(parameters.getRep());
        medida.setTiempo(parameters.getTemp());
        medida.setNumMuestras(parameters.getNumSample());
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

        for(int i =1; i == medida.getRepeticiones();i++){
            Muestras mm = new Muestras(medida.getMedidaid(),i);
            db.muestrasDao().createMuestras(mm);
        }

        for (MuestraCapturada mc :
                lstMuestraCap) {
                int bsid = createBS(mc,db);     // Comprovar si hay bs, si no hay crear, si hay asignarle la id
                //obtenermuestrasid para asignarselo a muestra
                int muestrasId = (int) db.muestrasDao().getMuestrasIdByMedidaIdRep( medida.getMedidaid(), mc.getRepeticion());
                Muestra m = new Muestra(muestrasId, mc.getValor(),mc.getNummuestra(),bsid);
                db.muestraDao().createMuestra(m);
        }

        return muestrasids;
    }

    public List<Mapa> getAllMapas(AppRoomDatabase db){
        db.mapadao().getAllMapas();
        return null;
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
