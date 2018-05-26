package com.example.garci.positionsystemapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CargarMapaFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dailog_abrirmapa, container, false);
        getDialog().setTitle("Simple Dialog");

        Button dismiss = (Button) rootView.findViewById(R.id.btnCancelarMapa);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button nuevoMapa = (Button) rootView.findViewById(R.id.btnNuevoMapa);
        nuevoMapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openNuevoMap();
            }
        });


        return rootView;
    }

    public void openNuevoMap(){
        //((MainActivity)getActivity()).dispatchTakePictureIntent();
        FragmentManager fm = getFragmentManager();
        NuevoMapaDialogFragment nuevoMapaDialogFragment = new NuevoMapaDialogFragment ();
        nuevoMapaDialogFragment.show(fm, "Sample Fragment");

    }
}
