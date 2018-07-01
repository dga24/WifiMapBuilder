package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;

import java.util.List;

@Dao
public interface MapaDao {

    @Insert
    long createMapa(Mapa mapa);

    @Query("SELECT * FROM mapa WHERE mapaid = :mapaid")
    Mapa getMapa(int mapaid);

    @Query("Select * FROM mapa")
    List<Mapa> getAllMapas();

    @Query("UPDATE mapa " +
            "SET coordenadaid = :origenid " +
            "WHERE mapaid = :mapaid")
    long updateOrigenId(int mapaid, int origenid);

    @Query("SELECT * FROM mapa ORDER BY mapaid DESC LIMIT 1")
    long getLastMapid();
}
