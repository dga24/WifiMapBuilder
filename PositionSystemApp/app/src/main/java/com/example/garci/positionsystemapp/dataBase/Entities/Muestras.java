package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Medida.class,
                parentColumns = "medidaid",
                childColumns = "medidaid"
        ),
        @ForeignKey(entity = EstacionBase.class,
                parentColumns = "bsid",
                childColumns = "bsid"
        )})
public class Muestras {

    @PrimaryKey(autoGenerate = true)
    private int muestrasid;
    private int medidaid;
    private int repeticion;
    private int bsid;

    public Muestras(int muestrasid, int medidaid, int repeticion, int bsid) {
        this.muestrasid = muestrasid;
        this.medidaid = medidaid;
        this.repeticion = repeticion;
        this.bsid = bsid;
    }

    public Muestras(int medidaid, int repeticion, int bsid) {
        this.medidaid = medidaid;
        this.repeticion = repeticion;
        this.bsid = bsid;
    }

    public int getMuestrasid() {
        return muestrasid;
    }

    public void setMuestrasid(int muestrasid) {
        this.muestrasid = muestrasid;
    }

    public int getMedidaid() {
        return medidaid;
    }

    public void setMedidaid(int medidaid) {
        this.medidaid = medidaid;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public int getBsid() {
        return bsid;
    }

    public void setBsid(int bsid) {
        this.bsid = bsid;
    }
}

