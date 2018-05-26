package com.example.garci.positionsystemapp.dataBase;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppRoomDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppRoomDatabase db) {
        populateWithTestData(db);
    }


    private static void populateWithTestData(AppRoomDatabase db) {
        EstacionBase bs = new EstacionBase("WLAN1","FF:FF:FF:FF:FF:FF","WIFI");
        db.estacionBaseDao().createEstacionBase(bs);
        System.out.print("\r\n\n\n\nRows Count: " + db.estacionBaseDao().getAllEstacionBase()+"\n\n\n");
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + db.estacionBaseDao().getAllEstacionBase());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppRoomDatabase mDb;

        PopulateDbAsync(AppRoomDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
