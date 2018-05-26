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

    @Query("SELECT DISTINCT bsid FROM muestra " +
            "WHERE muestrasid =:muestrasId")
    int getDifferentBsid(int muestrasId);

    // 3. Para cada BSID obtener lista de muestras
    @Query("SELECT * FROM muestra " +
            "WHERE bsid =:bsid AND muestrasid =:muestrasid")
    List<Muestra> getLstMuestraByBsidAndMuestrasId(int bsid, int muestrasid);

}
