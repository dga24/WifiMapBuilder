package com.example.garci.positionsystemapp.model;

import android.arch.lifecycle.ViewModel;

public class EstacionBaseModel{

    private String bsid;
    private String ssid;
    private String mac;
    private String tipo;


    public EstacionBaseModel(String bsid, String ssid, String mac, String tipo) {
        this.bsid = bsid;
        this.ssid = ssid;
        this.mac = mac;
        this.tipo = tipo;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
