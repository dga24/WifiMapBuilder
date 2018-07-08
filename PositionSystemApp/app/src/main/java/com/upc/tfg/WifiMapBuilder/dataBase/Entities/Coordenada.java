package com.upc.tfg.WifiMapBuilder.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Mapa.class,
        parentColumns = "mapaid",
        childColumns = "mapaid"))
public class Coordenada {

    @PrimaryKey(autoGenerate = true)
    Integer coordenadaid;
    Integer mapaid;
    double x;
    double y;
    double z;
    Integer pixelx;
    Integer pixely;


    public Coordenada(Integer mapaid, double x, double y, double z, Integer pixelx, Integer pixely) {
        this.mapaid = mapaid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pixelx = pixelx;
        this.pixely = pixely;
    }

    public Integer getCoordenadaid() {
        return coordenadaid;
    }

    public void setCoordenadaid(Integer coordenadaid) {
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

    public Integer getMapaid() {
        return mapaid;
    }

    public void setMapaid(Integer mapaid) {
        this.mapaid = mapaid;
    }

    public Integer getPixelx() {
        return pixelx;
    }

    public void setPixelx(Integer pixelx) {
        this.pixelx = pixelx;
    }

    public Integer getPixely() {
        return pixely;
    }

    public void setPixely(Integer pixely) {
        this.pixely = pixely;
    }

    public String toString(){
        return "("+x+","+y+","+z+")";
    }

}
