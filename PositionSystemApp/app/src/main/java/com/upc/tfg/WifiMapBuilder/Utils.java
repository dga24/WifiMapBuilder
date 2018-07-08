package com.upc.tfg.WifiMapBuilder;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;
import com.upc.tfg.WifiMapBuilder.model.quality.QualityCalculator;

import java.util.List;

public class Utils {

    static final int CARGAR_MAPA_PRECAPTURA=0;
    static final int CARGARM_APA_HEATMAP=0;


    public double calcularMedia(List<Muestra> muestras){
        double sum = 0;
        for (Muestra muestra :
                muestras) {
            sum = sum + muestra.getValor();
        }
        return Math.round((sum/muestras.size())*100)/100;
    }

    public double calcularVarianza(List<Muestra> muestras){
        double res = 0;
        double sum =0;
        double mean = calcularMedia(muestras);
        for (Muestra m :
                muestras) {
            sum =+ (m.getValor()-mean)*(m.getValor()-mean);
            res = sum/(muestras.size()-1);
        }
        return Math.round(res*100)/100;
    }

    public double computeQuality(QualityCalculator qualityCalculator, List<Muestra> samples, long totalSamples) {
        return qualityCalculator.compute(samples, totalSamples);
    }
}
