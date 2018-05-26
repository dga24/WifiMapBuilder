package com.example.garci.positionsystemapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NuevoMapaDialogFragment extends DialogFragment {

    EditText nombre;
    EditText edificio;
    EditText planta;
    ImageView captura;
    Button cargarFoto;
    Button btnGuardarMapa;
    Button btnCancelarMapa;

    Uri imgUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_nuevo_mapa, container, false);
        getDialog().setTitle("Simple Dialog");

        nombre = (EditText) rootView.findViewById(R.id.editNombreMapa);
        edificio = (EditText) rootView.findViewById(R.id.editEdificioMapa);
        planta = (EditText) rootView.findViewById(R.id.editPlantaMapa);
        cargarFoto = (Button) rootView.findViewById(R.id.cargarFoto);

        captura = (ImageView) rootView.findViewById(R.id.imgMapaCaptura);
        btnGuardarMapa = (Button) rootView.findViewById(R.id.btnGuardarMapa);
        btnCancelarMapa = (Button) rootView.findViewById(R.id.btnCancelarMapa);


        cargarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!nombre.getText().equals("")&&!edificio.getText().equals("")&&!planta.getText().equals("")){
                    takePicture();
                }
            }
        });

        btnCancelarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnGuardarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).createMapa(nombre.toString(),edificio.toString(),planta.toString(), imgUri);
            }
        });

        return rootView;
    }

    public void takePicture(){
        ((MainActivity)getActivity()).dispatchTakePictureIntent();
    }

    public void captureRealized(Uri imgUri){
        this.imgUri= imgUri;
        cargarFoto.setVisibility(View.GONE);
        captura.setImageURI(imgUri);
        captura.setVisibility(View.VISIBLE);
        btnCancelarMapa.setVisibility(View.VISIBLE);
        btnGuardarMapa.setVisibility(View.VISIBLE);
    }
}
