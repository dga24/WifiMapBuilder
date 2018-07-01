package com.upc.tfg.WifiMapBuilder.model.quality;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;

import java.util.List;

public interface QualityCalculator {
    double compute(List<Muestra> muestras, long totalSamples);
}
