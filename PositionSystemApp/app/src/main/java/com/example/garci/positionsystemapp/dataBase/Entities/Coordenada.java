package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Coordenada {

    @PrimaryKey(autoGenerate = true)
    int coordenadaid;
    int mapaid;
    double x;
    double y;
    double z;
    double pixelx;
    double pixely;


    public Coordenada(int mapaid, double x, double y, double z, double pixelx, double pixely) {
        this.mapaid = mapaid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pixelx = pixelx;
        this.pixely = pixely;
    }

    public int getCoordenadaid() {
        return coordenadaid;
    }

    public void setCoordenadaid(int coordenadaid) {
        this.coordenadaid = coordenadaid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getMapaid() {
        return mapaid;
    }

    public void setMapaid(int mapaid) {
        this.mapaid = mapaid;
    }

    public double getPixelx() {
        return pixelx;
    }

    public void setPixelx(double pixelx) {
        this.pixelx = pixelx;
    }

    public double getPixely() {
        return pixely;
    }

    public void setPixely(double pixely) {
        this.pixely = pixely;
    }

}
