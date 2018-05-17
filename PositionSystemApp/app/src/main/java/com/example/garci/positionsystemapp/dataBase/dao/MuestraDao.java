package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;

import java.util.List;

@Dao
public interface MuestraDao {

    @Query("SELECT * FROM muestra WHERE muestrasid =:muestrasid ")
    List<Muestra> getListaMuestras(int muestrasid);
}
