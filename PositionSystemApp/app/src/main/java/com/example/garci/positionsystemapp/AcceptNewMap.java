package com.example.garci.positionsystemapp;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garci.positionsystemapp.adapter.SeñalAdapter;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptNewMap extends DialogFragment {

    Mapa mapa;

    TextView name;
    TextView building;
    TextView floor;
    Button btnSaveMap;

    ImageView img;

    int pixelX;
    int pixelY;

    TextView tvPixelX;
    TextView tvPixelY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_new_map, container, false);

        name = (TextView) view.findViewById(R.id.tvAceptMapaName);
        building = (TextView) view.findViewById(R.id.tvAceptBuildingName);
        floor = (TextView) view.findViewById(R.id.tvAceptFloorName);

        img = (ImageView) view.findViewById(R.id.imgAcceptMapa);

        tvPixelX = (TextView) view.findViewById(R.id.tvPixelX);
        tvPixelY = (TextView) view.findViewById(R.id.tvPixelY);


        btnSaveMap = (Button) view.findViewById(R.id.btnsaveNewMap);
        name.setText(mapa.getNombre());
        building.setText(mapa.getEdificio());
        floor.setText(String.valueOf(mapa.getPlanta()));
        img.setImageURI(Uri.parse(mapa.getImgMapa()));

        btnSaveMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMapa();
            }
        });

        return view;
    }

    public void saveMapa(){
        ((MainActivity)getActivity()).saveMap(mapa);
        dismiss();
    }

    public void setMapa(Mapa mapa){
        this.mapa=mapa;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

}
