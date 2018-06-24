package com.example.garci.positionsystemapp.dataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Medida.class,
                parentColumns = "medidaid",
                childColumns = "medidaid" ))
public class Muestras {

    @PrimaryKey(autoGenerate = true)
    private Integer muestrasid;
    private Integer medidaid;
    private Integer repeticion;

    public Muestras(Integer medidaid, int repeticion) {
        this.medidaid = medidaid;
        this.repeticion = repeticion;
    }


    public Integer getMuestrasid() {
        return muestrasid;
    }

    public void setMuestrasid(Integer muestrasid) {
        this.muestrasid = muestrasid;
    }

    public Integer getMedidaid() {
        return medidaid;
    }

    public void setMedidaid(Integer medidaid) {
        this.medidaid = medidaid;
    }

    public Integer getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(Integer repeticion) {
        this.repeticion = repeticion;
    }

}

