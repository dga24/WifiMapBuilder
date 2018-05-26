package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

@Entity(foreignKeys = {@ForeignKey(entity = Mapa.class,
            parentColumns = "mapaid",
            childColumns = "mapaid"
        ),
        @ForeignKey(entity = Coordenada.class,
            parentColumns = "coordenadaid",
            childColumns = "posicionid"
        )
        })
public class Medida {

    @PrimaryKey(autoGenerate = true)
    private int medidaid;
    private int posicionid;
    private int angulo;
    private int mapaid;
    private String fechaInicio;
    private String fechaFin;
    private double periodo;
    private int repeticiones;
    private double tiempo;
    private int numMuestras;


    public Medida(int posicionid, int angulo, int mapaid, String fechaInicio, String fechaFin, double periodo, int repeticiones, double tiempo, int numMuestras) {
        this.posicionid = posicionid;
        this.angulo = angulo;
        this.mapaid = mapaid;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
        this.repeticiones = repeticiones;
        this.tiempo = tiempo;
        this.numMuestras = numMuestras;
    }

    public int getMedidaid() {
        return medidaid;
    }

    public void setMedidaid(int medidaid) {
        this.medidaid = medidaid;
    }

    public int getPosicionid() {
        return posicionid;
    }

    public void setPosicionid(int posicionid) {
        this.posicionid = posicionid;
    }

    public int getAngulo() {
        return angulo;
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }

    public int getMapaid() {
        return mapaid;
    }

    public void setMapaid(int mapaid) {
        this.mapaid = mapaid;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPeriodo() {
        return periodo;
    }

    public void setPeriodo(double periodo) {
        this.periodo = periodo;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public int getNumMuestras() { return numMuestras; }

    public void setNumMuestras(int numMuestras) { this.numMuestras = numMuestras; }
}
