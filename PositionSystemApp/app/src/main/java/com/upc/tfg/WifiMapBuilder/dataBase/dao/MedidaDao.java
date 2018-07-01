package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.EstacionBase;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;

import java.util.List;

@Dao
public interface MedidaDao {

    @Insert
    long createMedida(Medida medida);


    @Query("SELECT * FROM medida WHERE mapaid =:mapaid")
    List<Medida> getMedidasByMapaId(int mapaid);

    @Query("SELECT * FROM medida WHERE medidaid =:medidaid")
    Medida getMedidaById(int medidaid);


    @Query("SELECT * FROM medida WHERE medidaid IN (:medidasids)")
    List<Medida> getMedidasByIds(List<Integer> medidasids);


    @Query("SELECT * FROM medida WHERE posicionid =:posicionId")
    List<Medida> getMedidasByPosicionId(int posicionId);


}
