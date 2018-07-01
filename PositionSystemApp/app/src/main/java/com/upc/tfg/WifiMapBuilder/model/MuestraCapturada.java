package com.upc.tfg.WifiMapBuilder.model;

public class MuestraCapturada {

    private double valor;
    private Integer nummuestra;
    private Integer repeticion;
    private String bssid;
    private String ssid;
    private String tipo;

    public MuestraCapturada(double valor, Integer nummuestra, Integer repeticion, String bssid, String ssid, String tipo) {
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

    public Integer getNummuestra() {
        return nummuestra;
    }

    public void setNummuestra(Integer nummuestra) {
        this.nummuestra = nummuestra;
    }

    public Integer getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(Integer repeticion) {
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
