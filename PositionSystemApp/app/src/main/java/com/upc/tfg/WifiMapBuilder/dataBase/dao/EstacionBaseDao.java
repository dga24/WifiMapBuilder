package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.EstacionBase;

import java.util.List;

@Dao
public interface EstacionBaseDao {

    @Insert
    long createEstacionBase(EstacionBase estacionBase);

    @Query("SELECT * FROM estacionbase")
    List<EstacionBase> getAllEstacionBase();

    @Query("SELECT * FROM estacionbase WHERE bsid = :bsid")
    EstacionBase getEstacionBase(int bsid);

    @Query("SELECT * FROM estacionbase WHERE mac = :mac")
    EstacionBase getEstacionBaseByMac(String mac);

    @Query("SELECT EXISTS (SELECT * FROM estacionbase WHERE mac =:BSSID )")
    int existBs(String BSSID);

    @Query("SELECT bsid FROM estacionbase WHERE mac = :mac")
    int getBSIDByMac(String mac);


}
