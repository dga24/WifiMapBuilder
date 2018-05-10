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
        ),
        @ForeignKey(entity = EstacionBase.class,
            parentColumns = "bsid",
            childColumns = "bsid")
        })
public class Medida {

    @PrimaryKey(autoGenerate = true)
    private int medidaid;
    private int posicionid;
    private int angulo;
    private int mapaid;
    private String bsid;
    private Date fechaInicio;
    private Date fechaFin;
    private double periodo;
    private int repeticiones;
    private double tiempo;

    public Medida(int medidaid, int posicionid, int angulo, int mapaid, String bsid, Date fechaInicio, Date fechaFin, double periodo, int repeticiones, double tiempo) {
        this.medidaid = medidaid;
        this.posicionid = posicionid;
        this.angulo = angulo;
        this.mapaid = mapaid;
        this.bsid = bsid;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
        this.repeticiones = repeticiones;
        this.tiempo = tiempo;
    }

    public Medida(int posicionid, int angulo, int mapaid, String bsid, Date fechaInicio, Date fechaFin, double periodo, int repeticiones, double tiempo) {
        this.posicionid = posicionid;
        this.angulo = angulo;
        this.mapaid = mapaid;
        this.bsid = bsid;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
        this.repeticiones = repeticiones;
        this.tiempo = tiempo;
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

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
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
}
