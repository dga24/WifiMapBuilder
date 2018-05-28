package com.example.garci.positionsystemapp.dataBase;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.garci.positionsystemapp.MainActivity;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;

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
        EstacionBase bs = new EstacionBase("WLAN1","FF:FF:FF:FF:FF:FF","WIFI");
        db.estacionBaseDao().createEstacionBase(bs);
        Log.d(DatabaseInitializer.TAG, "Mac insertada:          " + db.estacionBaseDao().getAllEstacionBase().get(1).getMac().toString());
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
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
