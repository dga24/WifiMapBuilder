package com.example.garci.positionsystemapp.model.quality;

import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;

import java.util.List;

public interface QualityCalculator {
    double compute(List<Muestra> muestras, long totalSamples);
}
