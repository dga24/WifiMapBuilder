package com.upc.tfg.WifiMapBuilder.model.quality;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;

import java.util.List;

public class QualityCalculatorByRSSThreshold implements QualityCalculator {

    @Override
    public double compute(List<Muestra> samples, long totalSamples) {
        return samples.size()/totalSamples;
    }
}
