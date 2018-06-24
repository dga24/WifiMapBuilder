package com.example.garci.positionsystemapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.garci.positionsystemapp.adapter.CargarMapaAdapter;
import com.example.garci.positionsystemapp.adapter.CargarMapaHeatMapAdapter;
import com.example.garci.positionsystemapp.adapter.SeñalAdapter;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;

import java.util.List;

public class CargarMapaHeatMap extends DialogFragment {

    private RecyclerView recyclerListaMapas;
    private CargarMapaHeatMapAdapter adaptador;
    private Context context;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_abrir_mapa_heatmap, container, false);
        getDialog().setTitle("Simple Dialog");

        recyclerListaMapas = (RecyclerView) rootView.findViewById(R.id.rvListaMapa);
        recyclerListaMapas.setLayoutManager(new LinearLayoutManager(getContext()));




        return rootView;
    }

    public void openNuevoMap(){
        //((MainActivity)getActivity()).dispatchTakePictureIntent();
        FragmentManager fm = getFragmentManager();
        NuevoMapaDialogFragment nuevoMapaDialogFragment = new NuevoMapaDialogFragment ();
        nuevoMapaDialogFragment.show(fm, "Sample Fragment");
        dismiss();

    }

    public void inicializarAdaptador(List<Mapa> mapas,Context context,FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        this.context = context;
        adaptador = new CargarMapaHeatMapAdapter(mapas);
        adaptador.setContext(context);
        adaptador.setFragmentManager(fragmentManager);
        adaptador.setDialog(getDialog());
        recyclerListaMapas.setAdapter(adaptador);
    }
}