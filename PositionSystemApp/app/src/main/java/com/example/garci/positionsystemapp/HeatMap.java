package com.example.garci.positionsystemapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.garci.positionsystemapp.adapter.SeñalAdapter;
import com.example.garci.positionsystemapp.model.APSignalStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeatMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HeatMap extends Fragment {

    private List<APSignalStatistics> signals;
    private RecyclerView recyclerListaSignals;
    private SeñalAdapter adaptador;
    ImageView imgMapaCapturahm;

    private ca.hss.heatmaplib.HeatMap heatMap;

    private OnFragmentInteractionListener mListener;

    public HeatMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heatmap, container, false);

        signals = new ArrayList<>();
        recyclerListaSignals = (RecyclerView) view.findViewById(R.id.rvLista);
        recyclerListaSignals.setLayoutManager(new LinearLayoutManager(getContext()));
        imgMapaCapturahm = (ImageView) view.findViewById(R.id.imgMapaCapturahm);
        heatMap = (ca.hss.heatmaplib.HeatMap) view.findViewById(R.id.heatmapImg);
        int hh =imgMapaCapturahm.getDrawable().getIntrinsicWidth();
        int hhh =imgMapaCapturahm.getDrawable().getIntrinsicHeight();
        heatMap.setMinimum(10);
        heatMap.setMaximum(100.0);
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            ca.hss.heatmaplib.HeatMap.DataPoint point = new ca.hss.heatmaplib.HeatMap.DataPoint(rand.nextFloat(), rand.nextFloat(), rand.nextDouble() * 100.0);
            heatMap.addData(point);
        }

        data();
        inicializarAdaptador();
        return view;
    }

    public void data(){
        signals.add(new APSignalStatistics("ssid1","aa:ff:ff:ff",5,3,1));
        signals.add(new APSignalStatistics("ssid2","bb:ff:ff:ff",5,3,1));
        signals.add(new APSignalStatistics("ssid3","bb:ff:ff:ff",5,3,1));
        signals.add(new APSignalStatistics("ssid4","bb:ff:ff:ff",5,3,1));

    }



    public void inicializarAdaptador(){

        adaptador = new SeñalAdapter(signals);
        recyclerListaSignals.setAdapter(adaptador);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
