package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;

import java.util.List;

@Dao
public interface MedidaDao {

    @Insert
    public int createMedida(Medida medida);

    @Query("SELECT * FROM medida WHERE mapaid =:mapaid")
    public List<Medida> getMedidasMapa(int mapaid);
}
