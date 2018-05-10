package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;

@Entity(foreignKeys = @ForeignKey(entity = Coordenada.class,
                        parentColumns = "coordenadaid",
                        childColumns = "coordenadaorigenid"))
public class Mapa {

    @PrimaryKey(autoGenerate = true)
    private int mapaid;
    private String nombre;
    private String edificio;
    private int planta;
    private Image imgMapa;
    private int coordenadaorigenid;

    public Mapa(int mapaid, String nombre, String edificio, int planta, Image imgMapa, int coordenadaorigenid) {
        this.mapaid = mapaid;
        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.imgMapa = imgMapa;
        this.coordenadaorigenid = coordenadaorigenid;
    }

    public Mapa(String nombre, String edificio, int planta, Image imgMapa, int coordenadaorigenid) {
        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.imgMapa = imgMapa;
        this.coordenadaorigenid = coordenadaorigenid;
    }

    public int getMapaid() {
        return mapaid;
    }

    public void setMapaid(int mapaid) {
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

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public Image getImgMapa() {
        return imgMapa;
    }

    public void setImgMapa(Image imgMapa) {
        this.imgMapa = imgMapa;
    }

    public int getCoordenadaorigenid() {
        return coordenadaorigenid;
    }

    public void setCoordenadaorigenid(int coordenadaorigenid) {
        this.coordenadaorigenid = coordenadaorigenid;
    }
}
