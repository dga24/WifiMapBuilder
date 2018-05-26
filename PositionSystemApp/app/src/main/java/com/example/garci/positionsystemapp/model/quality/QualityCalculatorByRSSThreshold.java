package com.example.garci.positionsystemapp.model.quality;

import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;

import java.util.List;

public class QualityCalculatorByRSSThreshold implements QualityCalculator {

    @Override
    public double compute(List<Muestra> samples, long totalSamples) {
        return samples.size()/totalSamples;
    }
}
