package com.example.garci.positionsystemapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.garci.positionsystemapp.dataBase.AppRoomDatabase;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;
import com.example.garci.positionsystemapp.model.ITask;
import com.example.garci.positionsystemapp.model.Manager;
import com.example.garci.positionsystemapp.model.MuestraCapturada;
import com.example.garci.positionsystemapp.model.OnFinishListener;
import com.example.garci.positionsystemapp.model.Parameters;

import java.io.BufferedReader;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreCaptura.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreCaptura extends Fragment {


    private OnFragmentInteractionListener mListener;

    Manager manager;
    AppRoomDatabase db;
    Mapa mapa;
    Coordenada coordenada;
    int angle;
    int pixelX = 0;
    int pixelY = 0;


    AbrirMapaDialog abrirMapa;

    EditText txtX;
    EditText txtY;
    EditText txtZ;
    EditText txtAng;
    Button btnSelect;
    ImageView imgMapaCaptura;

    TextView tvPixelX;
    TextView tvPixelY;

    EditText editMuestras;
    EditText editPeriodo;
    EditText editRepeticiones;
    EditText editTiempo;
    Button btnStartCaptura;
    ImageButton refreshMap;

    TextView tvWifiScan;
    Button btnRefreshScan;

    SimpleScanWifi simpleScanWifi;

    Canvas canvas;

    Paint mPaint;

    boolean haveCoor0 = true;

    ConstraintLayout newMapaParam;
    ConstraintLayout paramPreCaptura;



    public PreCaptura() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pre_captura, container, false);

        txtX = (EditText) view.findViewById(R.id.editX);
        txtY = (EditText) view.findViewById(R.id.editY);
        txtZ = (EditText) view.findViewById(R.id.editZ);
        txtAng = (EditText) view.findViewById(R.id.editAng);
        btnSelect = (Button) view.findViewById(R.id.btnSelect);
        imgMapaCaptura = (ImageView) view.findViewById(R.id.imgMapaCaptura);

        tvPixelX = (TextView) view.findViewById(R.id.tvPixelX);
        tvPixelY = (TextView) view.findViewById(R.id.tvPixelY);

        editMuestras = (EditText) view.findViewById(R.id.editMuestras);
        editPeriodo = (EditText) view.findViewById(R.id.editPeriodo);
        editRepeticiones = (EditText) view.findViewById(R.id.editRepeticiones);
        editTiempo = (EditText) view.findViewById(R.id.editTiempo);
        btnStartCaptura = (Button) view.findViewById(R.id.btnStartCaptura);

        tvWifiScan = (TextView) view.findViewById(R.id.tvWifiScan);
        btnRefreshScan = (Button) view.findViewById(R.id.btnRefreshScan);

        newMapaParam = (ConstraintLayout) view.findViewById(R.id.newMapaParam);
        paramPreCaptura = (ConstraintLayout) view.findViewById(R.id.contentParam);

        refreshMap = (ImageButton) view.findViewById(R.id.btnRefreshImagen);

        simpleScanWifi = new SimpleScanWifi(getActivity());



        manager = ((MainActivity) getActivity()).getManager();
        db = ((MainActivity) getActivity()).getDb();


        cargarMapa();



        btnRefreshScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleScanWifi = new SimpleScanWifi(getActivity());
                simpleScanWifi.execute();
            }
        });

        refreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMapa();
            }
        });
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        btnStartCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenada = new Coordenada(mapa.getMapaid(), Double.parseDouble(txtX.getText().toString()),
                                            Double.parseDouble(txtY.getText().toString()),
                                            Double.parseDouble(txtZ.getText().toString()),
                                            pixelX,pixelY);
                angle = Integer.parseInt(txtAng.getText().toString());
                startCaptura();
            }
        });

        canvas = new Canvas();
        imgMapaCaptura.setOnTouchListener(new View.OnTouchListener(){
            final Bitmap bitmap = ((BitmapDrawable)imgMapaCaptura.getDrawable()).getBitmap();
            @Override
            public boolean onTouch(View v, MotionEvent event){
                pixelX = (int)event.getRawX();
                pixelY = (int)event.getRawY();
                //int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());
                tvPixelX.setText(String.valueOf(pixelX));
                tvPixelY.setText(String.valueOf(pixelY));
                canvas.drawPoint(pixelX,pixelY, mPaint);
                return true;
            }
        });


        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void openMap(){
        ((MainActivity)getActivity()).dispatchTakePictureIntent();
    }

    public void updateMap(Uri uri){
        imgMapaCaptura.setImageURI(uri);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void startCaptura(){
        Parameters parameters = new Parameters(Double.parseDouble(editPeriodo.getText().toString()),
                                Integer.valueOf(editRepeticiones.getText().toString()),
                                Double.parseDouble(editTiempo.getText().toString()),
                                Integer.valueOf(editMuestras.getText().toString()));
        ((MainActivity)getActivity()).startScan(parameters, coordenada, angle);
    }

    public void showAcceptNewMap(Mapa mapa){
        FragmentManager fm = getFragmentManager();
        AcceptNewMap acceptNewMap = new AcceptNewMap();
        acceptNewMap.setMapa(mapa);
        acceptNewMap.show(getActivity().getSupportFragmentManager(),"Fdialog");
//        Bundle args= new Bundle();
//        args.putString("name", mapa.getNombre());
//        args.putString("building", mapa.getEdificio());
//        args.putInt("floor", mapa.getPlanta());
//        args.putString("imgUriString", mapa.getImgMapa().toString());
//        acceptNewMap.setArguments(args);

//        acceptNewMap.show(getFragmentManager(),"acceptnewmapFragment");
    }

    public void  cargarMapa(){
        FragmentManager fm = getFragmentManager();
        CargarMapaDialogFragment cargarMapaDialogFragment = new CargarMapaDialogFragment();
        cargarMapaDialogFragment.show(fm, "Sample Fragment");
    }

    public void changeMap(Mapa mapa){
        this.mapa = mapa;
        imgMapaCaptura.setImageURI(Uri.parse(mapa.getImgMapa()));
        simpleScanWifi.execute();
        if(mapa.getCoordenadaid()==null){
            haveCoor0=false;
            txtX.setText(String.valueOf(0));
            txtY.setText(String.valueOf(0));
            paramPreCaptura.setVisibility(View.GONE);
            newMapaParam.setVisibility(View.VISIBLE);
            btnSelect.setVisibility(View.INVISIBLE);
        }
        else{
            haveCoor0=true;
            paramPreCaptura.setVisibility(View.VISIBLE);
            newMapaParam.setVisibility(View.GONE);
            btnSelect.setVisibility(View.VISIBLE);
        }
    }
}
