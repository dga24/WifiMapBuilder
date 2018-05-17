package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Medida.class,
                parentColumns = "medidaid",
                childColumns = "medidaid" ))
public class Muestras {

    @PrimaryKey(autoGenerate = true)
    private int muestrasid;
    private int medidaid;
    private int repeticion;

    public Muestras(int muestrasid, int medidaid, int repeticion) {
        this.muestrasid = muestrasid;
        this.medidaid = medidaid;
        this.repeticion = repeticion;
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

}

