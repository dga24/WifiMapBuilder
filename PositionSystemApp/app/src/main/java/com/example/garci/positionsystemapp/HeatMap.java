package com.example.garci.positionsystemapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.garci.positionsystemapp.adapter.SeñalAdapter;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;
import com.example.garci.positionsystemapp.model.APSignalStatistics;
import com.example.garci.positionsystemapp.model.PointCoverage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private ImageView imgMapaCapturahm;

    private Button btnQualityViewer;
    private Button btnCoverageviewr;

    private ca.hss.heatmaplib.HeatMap heatMapImg;

    private OnFragmentInteractionListener mListener;

    Drawable drawable;

    private Mapa mapa;

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
        btnQualityViewer = (Button) view.findViewById(R.id.btnQualityViewer);
        btnCoverageviewr = (Button)view.findViewById(R.id.btnCoverageviewr);
        heatMapImg = (ca.hss.heatmaplib.HeatMap) view.findViewById(R.id.heatmapImg);
        int hh =imgMapaCapturahm.getDrawable().getIntrinsicWidth();
        int hhh =imgMapaCapturahm.getDrawable().getIntrinsicHeight();

        heatMapImg.setMinimum(10);
        heatMapImg.setMaximum(100.0);
        heatMapImg.setRadius(80.0);
        Map<Float, Integer> colors = new ArrayMap<>();
        //build a color gradient in HSV from red at the center to green at the outside
        for (int i = 0; i < 21; i++) {
            float stop = ((float)i) / 20.0f;
            int color = doGradient(i * 5, 0, 100, 0xff00ff00, 0xffff0000);
            colors.put(stop, color);
        }
        heatMapImg.setColorStops(colors);


//        Random rand = new Random();
//        for (int i = 0; i < 20; i++) {
//            float x =rand.nextFloat();
//            float y =rand.nextFloat();
//            ca.hss.heatmaplib.HeatMap.DataPoint point = new  ca.hss.heatmaplib.HeatMap.DataPoint(x, y, rand.nextDouble() * 100.0);
//            heatMapImg.addData(point);
//        }


        btnCoverageviewr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).coverageFilter(mapa,getContext());
            }
        });

        btnQualityViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(getArguments() != null) {
            if ((getArguments().getString("action").equals("comprovarMapa"))) {//se acaba de crear/abrir un nuevo mapa -> cargar mapa y pantalla para indicar Origen
                int id = (getArguments().getInt("mapaid"));
                mapa = ((MainActivity) getActivity()).getMapa(id);
                changeMap(mapa);
            }
            if ((getArguments().getString("action").equals("cargarMapa"))) { //abrir dialog cargarMapa
                cargarMapa();
            }
        }

//        data();
//        inicializarAdaptador();
        return view;
    }

    public void changeMap(Mapa mapa){
        this.mapa = mapa;
        imgMapaCapturahm.setImageURI(Uri.parse(mapa.getImgMapa().toString()));
        heatMapImg.getWidth();
        heatMapImg.getHeight();
        //heatMapImg.setLayoutParams(new ViewGroup.LayoutParams(imgMapaCapturahm.getLayoutParams()));
        drawable = imgMapaCapturahm.getDrawable();
    }

    public void cargarMapa(){
        ((MainActivity)getActivity()).cargarHeatMap();
    }

    public void setCoverageFilter(Mapa mapa, List<PointCoverage> pointCoverages){
        btnCoverageviewr.setText("Coverage Viewre T");
        int i = 0;
        while(i<pointCoverages.size()){
            int aps =pointCoverages.get(i).getAps();
            Integer x = pointCoverages.get(i).getCoordenada().getPixelx();
            Integer y = pointCoverages.get(i).getCoordenada().getPixely();
            float w = imgMapaCapturahm.getWidth();
            float h = imgMapaCapturahm.getHeight();
            float mw = imgMapaCapturahm.getMeasuredWidth();
            float mh = imgMapaCapturahm.getMeasuredHeight();
            float xx = x/mw;
            float yy = y/mh;
            ca.hss.heatmaplib.HeatMap.DataPoint point = new ca.hss.heatmaplib.HeatMap.DataPoint(xx, yy,aps*10);
            heatMapImg.addData(point);
            heatMapImg.refreshDrawableState();
            heatMapImg.forceRefresh();
            i++;
        }
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

    private static int doGradient(double value, double min, double max, int min_color, int max_color) {
        if (value >= max) {
            return max_color;
        }
        if (value <= min) {
            return min_color;
        }
        float[] hsvmin = new float[3];
        float[] hsvmax = new float[3];
        float frac = (float)((value - min) / (max - min));
        Color.RGBToHSV(Color.red(min_color), Color.green(min_color), Color.blue(min_color), hsvmin);
        Color.RGBToHSV(Color.red(max_color), Color.green(max_color), Color.blue(max_color), hsvmax);
        float[] retval = new float[3];
        for (int i = 0; i < 3; i++) {
            retval[i] = interpolate(hsvmin[i], hsvmax[i], frac);
        }
        return Color.HSVToColor(retval);
    }

    private static float interpolate(float a, float b, float proportion) {
        return (a + ((b - a) * proportion));
    }
}
