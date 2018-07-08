package com.upc.tfg.WifiMapBuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static java.lang.Thread.sleep;

public class SimpleScanWifi extends AsyncTask<Void, Void, String> {

    Context context;
    Activity activity;
    ProgressDialog pDialog;
    Button button;

    public SimpleScanWifi(Activity activity) {
        this.context = activity;
        this.activity = activity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {
        String infoWifi = "";
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // Level of a Scan Result
            wifiManager.startScan();
            List<ScanResult> wifiList = wifiManager.getScanResults();
            long timestamp = 0;
            Log.i("scanWifi", "nuevo escaneo");
            for (ScanResult scanResult : wifiList) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timestamp = scanResult.timestamp;
                }
                infoWifi += "  SSID: " + scanResult.SSID +"\n MAC: " + scanResult.BSSID + "\n  POTENCIA[dBm]: " + scanResult.level + "\n\n";
            }
        return infoWifi;
    }

    protected void onPostExecute(String infowifi){
        pDialog.dismiss();
        TextView tvList = (TextView) activity.findViewById(R.id.tvWifiScanPreCaptura);
        tvList.setText(infowifi);
    }
}
