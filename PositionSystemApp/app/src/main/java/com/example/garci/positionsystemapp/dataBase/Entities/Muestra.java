package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Muestras.class,
            parentColumns = "muestrasid",
            childColumns = "muestrasid"))
public class Muestra {

    @PrimaryKey(autoGenerate = true)
    private int muestraid;
    private double valor;
    private int nummuestra;
    private String bsid;

    public Muestra(int muestraid, double valor, int nummuestra, String bsid) {
        this.muestraid = muestraid;
        this.valor = valor;
        this.nummuestra = nummuestra;
        this.bsid = bsid;
    }

    public Muestra(double valor, int nummuestra, String bsid) {
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
}
