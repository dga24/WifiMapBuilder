package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;

@Dao
public interface CoordenadaDao {

    @Insert
    long insertCoordenada(Coordenada coordenana);

    @Query("DELETE FROM coordenada WHERE coordenadaid = :coordenadaid")
    int deleteCoordenadaById(int coordenadaid);

    @Query("SELECT * FROM coordenada WHERE coordenadaid = :coordenadaid")
    Coordenada getCoordenada(int coordenadaid);
}
