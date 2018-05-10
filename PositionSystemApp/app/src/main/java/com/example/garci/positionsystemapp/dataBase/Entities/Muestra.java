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

    public Muestra(int muestraid, double valor, int nummuestra) {
        this.muestraid = muestraid;
        this.valor = valor;
        this.nummuestra = nummuestra;
    }

    public Muestra(double valor, int nummuestra) {
        this.valor = valor;
        this.nummuestra = nummuestra;
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
