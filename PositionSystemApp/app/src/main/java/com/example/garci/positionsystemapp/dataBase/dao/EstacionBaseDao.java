package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;

@Dao
public interface EstacionBaseDao {

    @Insert
    public int createEstacionBase(EstacionBase estacionBase);

    @Query("SELECT * FROM estacionbase WHERE bsid = :bsid")
    public EstacionBase getEstacionBase(int bsid);

    @Query("SELECT * FROM estacionbase WHERE mac = :mac")
    public EstacionBase getEstacionBaseByMac(String mac);


}
