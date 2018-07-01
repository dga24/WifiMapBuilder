package com.upc.tfg.WifiMapBuilder.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestras;

import java.util.List;

@Dao
public interface MuestrasDao {

    @Query("SELECT * FROM muestras WHERE medidaid =:medidaid")
    List<Muestras> getMuestrasByMedidaId(int medidaid);

    @Insert
    long createMuestras(Muestras muestras);

    @Query("SELECT muestrasid FROM muestras WHERE medidaid =:medidaid AND repeticion =:rep")
    long getMuestrasIdByMedidaIdRep(int medidaid,int rep);
}
