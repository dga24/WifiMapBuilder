package com.example.garci.positionsystemapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.garci.positionsystemapp.MainActivity;
import com.example.garci.positionsystemapp.R;

public class NuevoMapaDialogFragment extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nuevo_mapa, container, false);
        getDialog().setTitle("Simple Dialog");

        EditText nombre = (EditText) rootView.findViewById(R.id.editNombreMapa);
        EditText edificio = (EditText) rootView.findViewById(R.id.editEdificioMapa);
        EditText planta = (EditText) rootView.findViewById(R.id.editPlantaMapa);


        Button cargarFoto = (Button) rootView.findViewById(R.id.cargarFoto);
        cargarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openMap();
            }
        });


        return rootView;
    }

    public void openMap(){
        ((MainActivity)getActivity()).dispatchTakePictureIntent();

    }
}
