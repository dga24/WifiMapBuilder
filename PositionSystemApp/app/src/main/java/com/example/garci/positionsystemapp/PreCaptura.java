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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreCaptura.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreCaptura extends Fragment {


    private OnFragmentInteractionListener mListener;

    AbrirMapaDialog abrirMapa;

    EditText txtX;
    EditText txtY;
    EditText txtZ;
    EditText editAng;
    Button btnSelect;
    ImageView imgMapaCaptura;

    EditText editMuestras;
    EditText editPeriodo;
    EditText editRepeticiones;
    EditText editTiempo;
    Button btnStart;
    ImageButton refreshMap;

    TextView tvWifiScan;
    Button btnRefreshScan;

    SimpleScanWifi simpleScanWifi;

    Canvas canvas;

    Paint mPaint;



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
        editAng = (EditText) view.findViewById(R.id.editAng);
        btnSelect = (Button) view.findViewById(R.id.btnSelect);
        imgMapaCaptura = (ImageView) view.findViewById(R.id.imgMapaCaptura);

        editMuestras = (EditText) view.findViewById(R.id.editMuestras);
        editPeriodo = (EditText) view.findViewById(R.id.editPeriodo);
        editRepeticiones = (EditText) view.findViewById(R.id.editRepeticiones);
        editTiempo = (EditText) view.findViewById(R.id.editTiempo);
        btnStart = (Button) view.findViewById(R.id.btnStart);

        tvWifiScan = (TextView) view.findViewById(R.id.tvWifiScan);
        btnRefreshScan = (Button) view.findViewById(R.id.btnRefreshScan);

        refreshMap = (ImageButton) view.findViewById(R.id.btnRefreshImagen);

        simpleScanWifi = new SimpleScanWifi(getActivity());
        simpleScanWifi.execute();

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
                FragmentManager fm = getFragmentManager();
                CargarMapaDialogFragment cargarMapaDialogFragment = new CargarMapaDialogFragment();
                cargarMapaDialogFragment.show(fm, "Sample Fragment");
            }
        });
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);




        canvas = new Canvas();
        imgMapaCaptura.setOnTouchListener(new View.OnTouchListener(){
            final Bitmap bitmap = ((BitmapDrawable)imgMapaCaptura.getDrawable()).getBitmap();
            @Override
            public boolean onTouch(View v, MotionEvent event){
                int x = (int)event.getRawX();
                int y = (int)event.getRawY();
                //int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());
                txtX.setText(String.valueOf(x));
                txtY.setText(String.valueOf(y));
                canvas.drawPoint(x,y, mPaint);
                return true;
            }
        });


//        refreshMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMap();
//            }
//        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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



}
