package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.Muestras;

import java.util.List;

@Dao
public interface MuestrasDao {

    @Query("SELECT * FROM muestras WHERE medidaid =:medidaid")
    public List<Muestras> getMuestrasMedida(int medidaid);
}
