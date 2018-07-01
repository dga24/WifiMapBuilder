package com.upc.tfg.WifiMapBuilder.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class EstacionBase {

    @PrimaryKey(autoGenerate = true)
    private Integer bsid;
    private String ssid;
    private String mac;
    private String tipo;

    public EstacionBase(String ssid, String mac, String tipo) {
        this.ssid = ssid;
        this.mac = mac;
        this.tipo = tipo;
    }

    public Integer getBsid() {
        return bsid;
    }

    public void setBsid(Integer bsid) {
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
