package com.example.garci.positionsystemapp.model;

public class Señal {
    private String ssid;
    private String mac;
    private double media;
    private double var;
    private double calidad;

    public Señal(String ssid, String mac, double media, double var, double calidad) {
        this.ssid = ssid;
        this.mac = mac;
        this.media = media;
        this.var = var;
        this.calidad = calidad;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getVar() {
        return var;
    }

    public void setVar(double var) {
        this.var = var;
    }

    public double getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }
}
