package com.example.garci.positionsystemapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.EstacionBase;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.model.MuestraCapturada;
import com.example.garci.positionsystemapp.model.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;


public class WifiScan extends AsyncTask<Void, Integer, Void> {

        private int mTotalSamples;
        private int mSamples;
        private Context context;
        private WifiManager wifiManager;
        private ProgressDialog pd;
        private Parameters parameters;
        private int mrep;
        private String ssid;
        private ReentrantLock mLock = new ReentrantLock();
        private Condition mCndNewSample = mLock.newCondition();
        private BroadcastReceiver mBroadcastReceiver;
        private List<EstacionBase> bss =null;
        private List<MuestraCapturada> lstMuestraCap;
        private Coordenada coordenada;
        private int angle;
        private Medida medida;


    public WifiScan(Parameters parameters, Medida medida, WifiManager wifiManager, Context context) {
            this.medida = medida;
            this.parameters = parameters;
            this.wifiManager = wifiManager;
            this.context = context;
            mrep=0;
            this.coordenada = coordenada;
            this.angle = angle;
        }


    @Override
        protected void onPreExecute() {
        lstMuestraCap = new ArrayList<>();
            pd =new ProgressDialog(context);
            mTotalSamples = parameters.getNumSample();
            pd.setMessage("Wifi scan [0/" + mTotalSamples + "]");
            // Maybe it's worthy to include a spinner here
            pd.setIndeterminate(false);
            pd.show();

            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context c, Intent intent) {
                    List<ScanResult> wifiList = wifiManager.getScanResults();
                    long timestamp = 0;
                    int numMuestra=0;
                    String infoWifi = "";
                    Log.i("ScanWifi", "New scan");
                    for (ScanResult scanResult : wifiList) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timestamp = scanResult.timestamp;
                        }
                        MuestraCapturada m= createMuestra(scanResult,numMuestra,mrep);
                        lstMuestraCap.add(m);
                        numMuestra++;
                        ssid =scanResult.SSID;
                        Log.d("WifiManager:    ", "MAC: " + scanResult.BSSID + "  SSID: " + scanResult.SSID + "" + "  POTENCIA[dBm]: " + scanResult.level + "Timestamp: " + String.valueOf(timestamp));
                    }

                    if (mSamples++ < mTotalSamples) {
                        try {
                            sleep((long) parameters.getPeriod()); // Change 800 by the actual time between samples
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        wifiManager.startScan();
                    }
                    else {
                        c.unregisterReceiver(this);
                    }

                    mLock.lock();
                    mCndNewSample.signal();
                    mLock.unlock();
                }
            };

            context.registerReceiver(mBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

            wifiManager.startScan();
        }

        @Override
        protected Void doInBackground(Void... params){
            mLock.lock();
            try {
                while (mrep < parameters.getRep()) {
                    publishProgress(mSamples, mTotalSamples, mrep);

                    while (mSamples < mTotalSamples) {
                        mCndNewSample.awaitUninterruptibly();
                        publishProgress(mSamples, mTotalSamples, mrep);
                    }

                    mrep++;

                    if (mrep < parameters.getRep()) {
                        mSamples=0;
                        publishProgress(mSamples, mTotalSamples, mrep);
                        //llamar a manager y pasar lstMuestra

                        try {
                            sleep((long) parameters.getTemp()); // Change 800 by the actual time between samples
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        context.registerReceiver(mBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                        wifiManager.startScan();
                    }
                }
            }
            finally {
                mLock.unlock();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pd.setMessage("Wifi scan [" + progress[0] + "/" + progress[1] + "]\nRepetition=: "+progress[2]+"\n");
        }

        @Override
        protected void onPostExecute(Void result){
            pd.dismiss();
            ((MainActivity)context).saveCapture(lstMuestraCap, parameters);
        }

    private MuestraCapturada createMuestra(ScanResult scanResult, int num, int rep){
        MuestraCapturada muestra = new MuestraCapturada(scanResult.level,num,rep,scanResult.BSSID,scanResult.SSID,"WIFI");
        return muestra;
    }

}




