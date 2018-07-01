package com.upc.tfg.WifiMapBuilder;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;

public class NuevoMapaDialogFragment extends DialogFragment {

    EditText name;
    EditText build;
    EditText planta;
    Button cargarFoto;


    Uri imgUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_nuevo_mapa, container, false);
        getDialog().setTitle("Simple Dialog");

        name = (EditText) rootView.findViewById(R.id.editNombreMapa);
        build = (EditText) rootView.findViewById(R.id.editEdificioMapa);
        planta = (EditText) rootView.findViewById(R.id.editPlantaMapa);
        cargarFoto = (Button) rootView.findViewById(R.id.cargarFoto);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!name.getText().toString().matches("")&&!build.getText().toString().matches("")&&!planta.getText().toString().matches("")){
                    cargarFoto.setEnabled(true);
                }else{
                    cargarFoto.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });     //enable/disable button tomar foto
        build.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!name.getText().toString().matches("")&&!build.getText().toString().matches("")&&!planta.getText().toString().matches("")){
                    cargarFoto.setEnabled(true);
                }else{
                    cargarFoto.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        planta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!name.getText().toString().matches("")&&!build.getText().toString().matches("")&&!planta.toString().isEmpty()){
                    cargarFoto.setEnabled(true);
                }else{
                    cargarFoto.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cargarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return rootView;
    }

    public void takePicture(){
        dismiss();
        ((MainActivity)getActivity()).newMap(name.getText().toString(), build.getText().toString(),planta.getText().toString());
    }


    }
