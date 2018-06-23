package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;

@Entity(foreignKeys = @ForeignKey(entity = Coordenada.class,
                        parentColumns = "coordenadaid",
                        childColumns = "coordenadaid"))
public class Mapa {

    @PrimaryKey(autoGenerate = true)
    private Integer mapaid;
    private String nombre;
    private String edificio;
    private Integer planta;
    private String imgMapa;
    private Integer coordenadaid;

    public Mapa(String nombre, String edificio, Integer planta, String imgMapa, Integer coordenadaid) {
        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.imgMapa = imgMapa;
        this.coordenadaid = coordenadaid;
    }


    public Integer getMapaid() {
        return mapaid;
    }

    public void setMapaid(Integer mapaid) {
        this.mapaid = mapaid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public Integer getPlanta() {
        return planta;
    }

    public void setPlanta(Integer planta) {
        this.planta = planta;
    }

    public String getImgMapa() {
        return imgMapa;
    }

    public void setImgMapa(String imgMapa) {
        this.imgMapa = imgMapa;
    }

    public Integer getCoordenadaid() {
        return coordenadaid;
    }

    public void setCoordenadaid(Integer coordenadaid) {
        this.coordenadaid = coordenadaid;
    }
}
