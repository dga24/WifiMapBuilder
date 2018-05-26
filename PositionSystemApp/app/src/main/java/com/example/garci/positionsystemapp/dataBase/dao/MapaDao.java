package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;

import java.util.List;

@Dao
public interface MapaDao {

    @Insert
    long createMapa(Mapa mapa);

    @Query("SELECT * FROM mapa WHERE mapaid = :mapaid")
    Mapa getMapa(int mapaid);

    @Query("Select * FROM mapa")
    List<Mapa> getAllMapas();







}
