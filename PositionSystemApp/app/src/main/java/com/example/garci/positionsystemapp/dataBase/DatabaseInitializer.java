package com.example.garci.positionsystemapp.dataBase;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.garci.positionsystemapp.MainActivity;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;

import java.util.List;

import static java.lang.Thread.sleep;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppRoomDatabase db, Context context) {
        PopulateDbAsync task = new PopulateDbAsync(db,context);
        task.execute();
    }

    public static void populateSync(@NonNull final AppRoomDatabase db) {
        populateWithTestData(db);
    }


    private static void populateWithTestData(AppRoomDatabase db) {
        EstacionBase bs = new EstacionBase("WLAN_TEST-DB","FF:FF:FF:FF:FF:FF","WIFI");
        Mapa mapa = new Mapa("Test","Test",0,"url",null);
        long mapaid = db.mapadao().createMapa(mapa);
        Coordenada coor = new Coordenada((int) mapaid,0,0,0,0,0);
        db.coordenadaDao().insertCoordenada(coor);
        db.estacionBaseDao().createEstacionBase(bs);
        Log.d(DatabaseInitializer.TAG, "WLAN insertada:          " + db.estacionBaseDao().getAllEstacionBase().get(0).getSsid().toString());
        int size =db.coordenadaDao().getAllCoordenadas().size();
        List<Coordenada> coordenadas = db.coordenadaDao().getAllCoordenadas();
        Log.d(DatabaseInitializer.TAG, "Coordenada insertada:          " + db.coordenadaDao().getAllCoordenadas().size());
        Log.d(DatabaseInitializer.TAG, "WLAN insertada:          " + db.mapadao().getMapa(1).getNombre().toString());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mProgress;
        private Context mcontext;

        private final AppRoomDatabase mDb;

        PopulateDbAsync(AppRoomDatabase db, Context context) {
            mDb = db;
            mcontext=context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            Log.d(DatabaseInitializer.TAG,"ahora el mio...");
            Log.d(DatabaseInitializer.TAG, String.valueOf(mDb.estacionBaseDao().getEstacionBaseByMac("FF:FF:FF:FF:FF:FF").getBsid()));
            Log.d(DatabaseInitializer.TAG, mDb.estacionBaseDao().getEstacionBase(1).getTipo().toString());
            onPostExecute(true);
            return null;
        }

        protected void onPreExecute(){
            mProgress = new ProgressDialog(mcontext);
            mProgress.setMessage("Reading/writing db...");
            mProgress.setTitle("Please wait...");
            mProgress.show();
        }

        protected void onPostExecute(boolean result){
            mProgress.dismiss();
        }




    }
}
