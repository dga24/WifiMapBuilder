package com.upc.tfg.WifiMapBuilder.dataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.EstacionBase;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestra;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Muestras;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.CoordenadaDao;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.EstacionBaseDao;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.MapaDao;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.MedidaDao;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.MuestraDao;
import com.upc.tfg.WifiMapBuilder.dataBase.dao.MuestrasDao;

@Database(entities = {Coordenada.class,
        EstacionBase.class,
        Mapa.class,
        Medida.class,
        Muestra.class,
        Muestras.class}, version = 1)

public abstract class AppRoomDatabase extends RoomDatabase {

    private static AppRoomDatabase INSTANCE;

    public abstract CoordenadaDao coordenadaDao();
    public abstract EstacionBaseDao estacionBaseDao();
    public abstract MapaDao mapadao();
    public abstract MedidaDao medidaDao();
    public abstract MuestraDao muestraDao();
    public abstract MuestrasDao muestrasDao();


    public static AppRoomDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, "appdatabase")
                                    .allowMainThreadQueries()
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
