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

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;


public class WifiScan extends AsyncTask<Void, Integer, Void> {

        private int mTotalSamples;
        private int mSamples;
        private Context context;
        private WifiManager wifiManager;
        private ProgressDialog pd;
        String ssid;
        ReentrantLock mLock = new ReentrantLock();
        Condition mCndNewSample = mLock.newCondition();
        BroadcastReceiver mBroadcastReceiver;


    public WifiScan(int mTotalSamples, int mSamples, WifiManager wifiManager,Context context) {
            this.mTotalSamples = mTotalSamples;
            this.mSamples = mSamples;
            this.wifiManager = wifiManager;
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            pd =new ProgressDialog(context);
            pd.setMessage("Wifi scan [0/" + mTotalSamples + "]");
            // Maybe it's worthy to include a spinner here
            pd.setIndeterminate(false);
            pd.show();

            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context c, Intent intent) {
                    List<ScanResult> wifiList = wifiManager.getScanResults();
                    long timestamp = 0;
                    String infoWifi = "";
                    Log.i("ScanWifi", "New scan");
                    for (ScanResult scanResult : wifiList) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timestamp = scanResult.timestamp;
                        }
                        ssid =scanResult.SSID;
                        Log.d("WifiManager:    ", "MAC: " + scanResult.BSSID + "  SSID: " + scanResult.SSID + "" +
                                "  POTENCIA[dBm]: " + scanResult.level + "Timestamp: " + String.valueOf(timestamp));
                        infoWifi += "MAC: " + scanResult.BSSID + "  SSID: " + scanResult.SSID + "" +
                                "\n  POTENCIA[dBm]: " + scanResult.level + " Timestamp: " + String.valueOf(timestamp) + "\n\n";
                    }

                    if (mSamples++ < mTotalSamples) {
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
                while (mSamples < mTotalSamples) {
                    try {
                        sleep(800); // Change 800 by the actual time between samples
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCndNewSample.awaitUninterruptibly();
                    publishProgress(mSamples, mTotalSamples);
                }
            }
            finally {
                mLock.unlock();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pd.setMessage("Wifi scan [" + progress[0] + "/" + progress[1] + "]\nSSID=: "+ssid+"\n");
        }

        @Override
        protected void onPostExecute(Void result){
            pd.dismiss();
        }
    }

