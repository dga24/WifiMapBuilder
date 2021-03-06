package com.upc.tfg.WifiMapBuilder.model;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;

public class PointCoverage {
    private Coordenada coordenada;
    private int Aps;

    public PointCoverage(Coordenada coordenada, int aps) {
        this.coordenada = coordenada;
        Aps = aps;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public int getAps() {
        return Aps;
    }

    public void setAps(int aps) {
        Aps = aps;
    }
}
