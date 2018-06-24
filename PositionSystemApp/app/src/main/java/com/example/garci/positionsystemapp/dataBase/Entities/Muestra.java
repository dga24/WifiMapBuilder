package com.example.garci.positionsystemapp.dataBase.Entities;

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
    private Integer muestrasid;
    private double valor;
    private Integer nummuestra;
    private Integer bsid;


    public Muestra(int muestrasid, double valor, Integer nummuestra, Integer bsid) {
        this.muestrasid = muestrasid;
        this.valor = valor;
        this.nummuestra = nummuestra;
        this.bsid = bsid;
    }

    public Integer getMuestrasid() {
        return muestrasid;
    }

    public void setMuestrasid(Integer muestrasid) {
        this.muestrasid = muestrasid;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getNummuestra() {
        return nummuestra;
    }

    public void setNummuestra(Integer nummuestra) {
        this.nummuestra = nummuestra;
    }

    public Integer getBsid() { return bsid; }

    public void setBsid(Integer bsid) { this.bsid = bsid; }
}
