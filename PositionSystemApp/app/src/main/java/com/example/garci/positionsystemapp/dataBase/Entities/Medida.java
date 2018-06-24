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
    private Integer medidaid;
    private Integer posicionid;
    private Integer angulo;
    private Integer mapaid;
    private String fechaInicio;
    private String fechaFin;
    private double periodo;
    private Integer repeticiones;
    private double tiempo;
    private Integer numMuestras;


    public Medida(Integer posicionid, Integer angulo, Integer mapaid, String fechaInicio, String fechaFin, double periodo, Integer repeticiones, double tiempo, Integer numMuestras) {
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

    public Integer getMedidaid() {
        return medidaid;
    }

    public void setMedidaid(Integer medidaid) {
        this.medidaid = medidaid;
    }

    public Integer getPosicionid() {
        return posicionid;
    }

    public void setPosicionid(Integer posicionid) {
        this.posicionid = posicionid;
    }

    public Integer getAngulo() {
        return angulo;
    }

    public void setAngulo(Integer angulo) {
        this.angulo = angulo;
    }

    public Integer getMapaid() {
        return mapaid;
    }

    public void setMapaid(Integer mapaid) {
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

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getNumMuestras() { return numMuestras; }

    public void setNumMuestras(Integer numMuestras) { this.numMuestras = numMuestras; }
}
