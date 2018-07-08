package com.upc.tfg.WifiMapBuilder.model;

public class APSignalStatistics implements BSSignalStatistics {
    private String ssid;
    private String mac;
    private double media;
    private double var;
    private double calidad;

    public APSignalStatistics(String ssid, String mac, double media, double var, double calidad) {
        this.ssid = ssid;
        this.mac = mac;
        this.media = media;
        this.var = var;
        this.calidad = calidad;
    }

    public APSignalStatistics() {
        this(null, null, 0, 0, 0);
    }

    @Override
    public BSSignalStatistics getInstance() {
        return new APSignalStatistics();
    }

    public String getName() {
        return ssid;
    }

    public void setName(String ssid) {
        this.ssid = ssid;
    }

    public String getAddress() {
        return mac;
    }

    public void setAddress(String mac) {
        this.mac = mac;
    }

    public double getMean() {
        return media;
    }

    public void setMean(double media) {
        this.media = media;
    }

    public double getVar() {
        return var;
    }

    public void setVar(double var) {
        this.var = var;
    }

    public double getQuality() {
        return calidad;
    }

    public void setQuality(double calidad) {
        this.calidad = calidad;
    }
}
