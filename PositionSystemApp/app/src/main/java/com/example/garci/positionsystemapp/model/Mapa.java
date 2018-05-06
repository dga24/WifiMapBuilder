package com.example.garci.positionsystemapp.model;

import android.media.Image;

public class Mapa {

    private int mapaid;
    private String nombre;
    private String edificio;
    private int planta;
    private Image imgMapa;


    public Mapa(int mapaid, String nombre, String edificio, int planta, Image imgMapa) {
        this.mapaid = mapaid;
        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.imgMapa = imgMapa;
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
}
