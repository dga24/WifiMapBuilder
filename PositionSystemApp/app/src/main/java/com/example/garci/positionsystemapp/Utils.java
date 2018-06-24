package com.example.garci.positionsystemapp;

import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;
import com.example.garci.positionsystemapp.model.quality.QualityCalculator;

import java.util.List;

public class Utils {

    public double calcularMedia(List<Muestra> muestras){
        double sum = 0;
        for (Muestra muestra :
                muestras) {
            sum = sum + muestra.getValor();
        }
        return sum/muestras.size();
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
        return res;
    }

    public double computeQuality(QualityCalculator qualityCalculator, List<Muestra> samples, long totalSamples) {
        return qualityCalculator.compute(samples, totalSamples);
    }
}
