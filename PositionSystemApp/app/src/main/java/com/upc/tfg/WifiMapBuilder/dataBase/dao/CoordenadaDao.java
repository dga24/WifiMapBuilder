package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;

import java.util.List;

@Dao
public interface CoordenadaDao {

    @Insert
    long insertCoordenada(Coordenada coordenana);

    @Query("DELETE FROM coordenada WHERE coordenadaid = :coordenadaid")
    int deleteCoordenadaById(int coordenadaid);

    @Query("SELECT * FROM coordenada WHERE coordenadaid = :coordenadaid")
    Coordenada getCoordenada(int coordenadaid);

    @Query("SELECT * FROM coordenada WHERE x=:x AND y =:y AND mapaid=:mapaid")
    Coordenada getCoordenadaByMapaAndPos(double x, double y,int mapaid);

    @Query("Select * FROM coordenada")
    List<Coordenada> getAllCoordenadas();
}
