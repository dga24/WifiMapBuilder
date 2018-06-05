package com.example.garci.positionsystemapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.garci.positionsystemapp.dataBase.AppRoomDatabase;
import com.example.garci.positionsystemapp.dataBase.DatabaseInitializer;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;
import com.example.garci.positionsystemapp.dataBase.Entities.Medida;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestra;
import com.example.garci.positionsystemapp.dataBase.Entities.Muestras;
import com.example.garci.positionsystemapp.model.Parameters;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PreCaptura.OnFragmentInteractionListener, HeatMap.OnFragmentInteractionListener, GestionChooser.OnFragmentInteractionListener {

    Context context;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;


    private Uri mImgUri;
    private PreCaptura mPreCaptura;
    private Captura mCaptura;
    private GestionChooser mGestionChooser;
    private GestionDatos mGestionDatos;
    private HeatMap mHeatMap;
    private AppRoomDatabase db;

    ImageButton btnRefresh;
    TextView txtWifi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        permissionRequest();
        this.context = this;

        btnRefresh = (ImageButton) findViewById(R.id.btnRefreshWifi);
        txtWifi = (TextView) findViewById(R.id.txtWifi);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanerWifi();
            }
        });


        //DatabaseInitializer.populateAsync(AppRoomDatabase.getAppDatabase(this),this);
        //Log.d("fin:    ","ya ne mainactivity.....");
        //db = AppRoomDatabase.getAppDatabase(this);
        //System.out.print(db.estacionBaseDao().getEstacionBase(1).getTipo().toString());



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentTransaction = false;
        Fragment fragment = null;

        if (id == R.id.capturaDatos) {
            fragment = mPreCaptura = new PreCaptura();
            fragmentTransaction=true;

        } else if (id == R.id.home) {
            fragment = mPreCaptura = new PreCaptura();
            fragmentTransaction=true;

        } else if (id == R.id.heatMap) {
            fragment = mHeatMap= new HeatMap();
            fragmentTransaction=true;

        } else if (id == R.id.gestorDatos) {
            fragment = mGestionChooser = new GestionChooser();
            fragmentTransaction = true;

        }
        if(fragmentTransaction){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    //Open Camera

    void newMap(String name, String build, String floor){
        dispatchTakePictureIntent();
    }

    void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.print("Error creando archivo imagen");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.garci.positionsystemapp.fileprovider",
                        photoFile);
                mImgUri = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //receive camera result

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(mImgUri==null) return;
            NuevoMapaDialogFragment nuevoMapaDialogFragment = (NuevoMapaDialogFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoMapa);
            nuevoMapaDialogFragment.captureRealized(mImgUri);
            //mPreCaptura.updateMap(mImgUri);
            mImgUri=null;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void createMapa(String nombre, String edificio, String planta, Uri img){
        Mapa mapa = new Mapa(nombre,edificio, Integer.parseInt(planta),img.getPath(),-1);
        System.out.print("datosrecibidos");
        db.mapadao().createMapa(mapa);
    }

    public void permissionRequest(){
        String[] PERMS_INITIAL={
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,


        };
        ActivityCompat.requestPermissions(this, PERMS_INITIAL, 127);
    }

//    public void scanWifi(){
//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        // Level of a Scan Result
//        int rep=10;
//        int i=0;
//        while(i<rep) {
//            try {
//                sleep(800);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            wifiManager.startScan();
//            List<ScanResult> wifiList = wifiManager.getScanResults();
//            long timestamp = 0;
//            String infoWifi = "";
//            Log.i("scanWifi", "nuevo escaneo");
//            for (ScanResult scanResult : wifiList) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    timestamp = scanResult.timestamp;
//                }
//                Log.d("wifimanager:    ", "MAC: " + scanResult.BSSID + "  SSID: " + scanResult.SSID + "  POTENCIA[dBm]: " + scanResult.level + "Timestamp: " + String.valueOf(timestamp));
//                infoWifi += "MAC: " + scanResult.BSSID + "  SSID: " + scanResult.SSID + "\n  POTENCIA[dBm]: " + scanResult.level + " Timestamp: " + String.valueOf(timestamp) + "\n\n";
//            }
//            i++;
//            //txtWifi.setText(infoWifi);
//        }
//    }

    public void scanerWifi() {
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled() == false) {
            wifiManager.setWifiEnabled(true);
        }

        int mSamples = 0;
        int mTotalSamples = 10;
        final WifiScan wifiScan = new WifiScan(mTotalSamples, mSamples, wifiManager,context);
        wifiScan.execute();
    }
}
