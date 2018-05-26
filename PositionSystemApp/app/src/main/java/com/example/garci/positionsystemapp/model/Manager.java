package com.example.garci.positionsystemapp.model;

import com.example.garci.positionsystemapp.Utils;
import com.example.garci.positionsystemapp.dataBase.AppRoomDatabase;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestras;
import com.example.garci.positionsystemapp.model.quality.QualityCalculator;
import com.example.garci.positionsystemapp.model.quality.QualityCalculatorByRSSThreshold;

import java.util.List;

public class Manager {

    AppRoomDatabase db;
    Utils utils;
    QualityCalculatorByRSSThreshold qualityCalculatorByRSSThreshold;


    // 1. Obtener número de muestras
    // 2. Obtener los distintos BSIDs
    // 3. Obtener los datos de cada BSID
    // 3. Para cada BSID obtener lista de muestras
    // 4. Para cada lista de muestras generar un BSSignalStatistics a partir del tipo
    // 5. Incluir el BSSignalStatics en la lista de salida.

    public List<BSSignalStatistics> getMuestrasByPosition(int medidaID, QualityCalculator qualityCalculator) {
        Medida medida = db.medidaDao().getMedidaById(medidaID);
        int numMuestras = medida.getNumMuestras();

        //listaMuestras en esa medida
        List<Muestras> lstmuestras = db.muestrasDao().getMuestrasByMedidaId(medidaID);

        List<Integer> bsids = null; //bsids diferentes


        //Con qe salga en una repeticion ya esta? ha de salir en todas?
        /*for (Muestras m :
                lstmuestras) {
            bsids.add(db.muestraDao().getDifferentBsid(m.getMuestrasid()));
        }*/
        bsids.add(db.muestraDao().getDifferentBsid(lstmuestras.get(1).getMuestrasid()));

        List<EstacionBase> bsss = null; //lista estaciones base
        for (int id :
                bsids) {
            bsss.add(db.estacionBaseDao().getEstacionBase(id));
        }

        List<Muestra> lstmuestra;
        List<BSSignalStatistics> bsSignalStatistics = null;
        double mean;
        double var;
        double quality;
        for (Muestras mm :
                lstmuestras) {
            for (int i :
                    bsids) {
                lstmuestra = db.muestraDao().getLstMuestraByBsidAndMuestrasId(i, mm.getMuestrasid());
                mean = utils.calcularMedia(lstmuestra);
                var = utils.calcularVarianza(lstmuestra);
                quality = utils.computeQuality(qualityCalculatorByRSSThreshold,lstmuestra, numMuestras);
                bsSignalStatistics.add(new APSignalStatistics(
                                                String.valueOf(i),
                                                db.estacionBaseDao().getEstacionBase(i).getMac(),
                                                0,0,0));
            }
        }
        return bsSignalStatistics;
    }

}
