package com.example.garci.positionsystemapp.model;

public class MuestraCapturada {

    private double valor;
    private int nummuestra;
    private int repeticion;
    private String bssid;
    private String ssid;
    private String tipo;

    public MuestraCapturada(double valor, int nummuestra, int repeticion, String bssid, String ssid, String tipo) {
        this.valor = valor;
        this.nummuestra = nummuestra;
        this.repeticion = repeticion;
        this.bssid = bssid;
        this.ssid = ssid;
        this.tipo = tipo;
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

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
