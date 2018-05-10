package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(foreignKeys = {@ForeignKey(entity = Mapa.class,
                                parentColumns = "mapaid",
                                childColumns = "mapaid"
                        ),
                        @ForeignKey(entity = Coordenada.class,
                                parentColumns = "coordenadaid",
                                childColumns = "coordenadaid"
                        )})
public class CoordenadaImagen {

    private int mapaid;
    private int coordenadaid;
    private int pixelX;
    private int pixelY;

    public CoordenadaImagen(int mapaid, int coordenadaid, int pixelX, int pixelY) {
        this.mapaid = mapaid;
        this.coordenadaid = coordenadaid;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    public int getMapaid() {
        return mapaid;
    }

    public void setMapaid(int mapaid) {
        this.mapaid = mapaid;
    }

    public int getCoordenadaid() {
        return coordenadaid;
    }

    public void setCoordenadaid(int coordenadaid) {
        this.coordenadaid = coordenadaid;
    }

    public int getPixelX() {
        return pixelX;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }
}
