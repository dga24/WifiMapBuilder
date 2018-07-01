package com.upc.tfg.WifiMapBuilder.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys ={ @ForeignKey(entity = Muestras.class,
            parentColumns = "muestrasid",
            childColumns = "muestrasid"),
            @ForeignKey(entity = EstacionBase.class,
            parentColumns = "bsid",
            childColumns = "bsid")
        })

public class Muestra {

    @PrimaryKey(autoGenerate = true)
    private int muestraid;
    private int muestrasid;
    private double valor;
    private int nummuestra;
    private int bsid;


    public Muestra(int muestrasid, double valor, Integer nummuestra, Integer bsid) {
        this.muestrasid = muestrasid;
        this.valor = valor;
        this.nummuestra = nummuestra;
        this.bsid = bsid;
    }

    public int getMuestraid() {
        return muestraid;
    }

    public void setMuestraid(int muestraid) {
        this.muestraid = muestraid;
    }

    public int getMuestrasid() {
        return muestrasid;
    }

    public void setMuestrasid(int muestrasid) {
        this.muestrasid = muestrasid;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getNummuestra() {
        return nummuestra;
    }

    public void setNummuestra(int nummuestra) {
        this.nummuestra = nummuestra;
    }

    public int getBsid() { return bsid; }

    public void setBsid(int bsid) { this.bsid = bsid; }
}
