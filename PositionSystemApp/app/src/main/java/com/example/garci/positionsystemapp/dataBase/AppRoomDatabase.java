package com.example.garci.positionsystemapp.dataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestras;
import com.example.garci.positionsystemapp.dataBase.dao.EstacionBaseDao;
import com.example.garci.positionsystemapp.dataBase.dao.MapaDao;
import com.example.garci.positionsystemapp.dataBase.dao.MedidaDao;
import com.example.garci.positionsystemapp.dataBase.dao.MuestraDao;
import com.example.garci.positionsystemapp.dataBase.dao.MuestrasDao;
import com.example.garci.positionsystemapp.dataBase.dao.coordenadaDao;

@Database(entities = {Coordenada.class,
        EstacionBase.class,
        Mapa.class,
        Medida.class,
        Muestra.class,
        Muestras.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static AppRoomDatabase INSTANCE;

    public abstract coordenadaDao coordenadaDao();
    public abstract EstacionBaseDao estacionBaseDao();
    public abstract MapaDao mMapadao();
    public abstract MedidaDao medidaDao();
    public abstract MuestraDao muestraDao();
    public abstract MuestrasDao muestrasDao();


    public static AppRoomDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, "appdatabase")
                                    .build();
                }
            }

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
