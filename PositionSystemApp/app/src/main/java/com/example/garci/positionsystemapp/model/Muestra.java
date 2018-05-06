package com.example.garci.positionsystemapp.model;

public class Muestra {

    private int muestraid;
    private double valor;


    public Muestra(int muestraid, double valor) {
        this.muestraid = muestraid;
        this.valor = valor;
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
}
