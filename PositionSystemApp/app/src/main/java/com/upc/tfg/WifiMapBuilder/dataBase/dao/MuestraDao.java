package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;

import java.util.List;

@Dao
public interface MuestraDao {

    @Insert
    long createMuestra(Muestra muestra);

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
