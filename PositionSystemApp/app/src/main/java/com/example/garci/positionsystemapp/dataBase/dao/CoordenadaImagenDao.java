package com.example.garci.positionsystemapp.dataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.garci.positionsystemapp.dataBase.Entities.CoordenadaImagen;

@Dao
public interface CoordenadaImagenDao {

    @Insert
    public int setCoordenadaImagen(CoordenadaImagen coordenadaImagen);
}
