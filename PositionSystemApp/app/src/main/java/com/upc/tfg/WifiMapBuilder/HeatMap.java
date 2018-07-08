package com.upc.tfg.WifiMapBuilder;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.upc.tfg.WifiMapBuilder.adapter.MedidaAdapter;
import com.upc.tfg.WifiMapBuilder.adapter.PointsAdapter;
import com.upc.tfg.WifiMapBuilder.adapter.SeñalAdapter;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;
import com.upc.tfg.WifiMapBuilder.model.APSignalStatistics;
import com.upc.tfg.WifiMapBuilder.model.PointCoverage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    List<Medida> medidas;
    List<String> coordenadasString;
    private List<Coordenada> coordenadas;
    private PointsAdapter pointsAdapter;
    private MedidaAdapter medidaAdapter;
    RecyclerView recyclerListaMedidas;
    private Spinner spPuntos;
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
        imgMapaCapturahm = (ImageView) view.findViewById(R.id.imgMapaCapturahm);
        btnQualityViewer = (Button) view.findViewById(R.id.btnQualityViewer);
        btnCoverageviewr = (Button)view.findViewById(R.id.btnCoverageviewr);
        heatMapImg = (ca.hss.heatmaplib.HeatMap) view.findViewById(R.id.heatmapImg);
        spPuntos = (Spinner) view.findViewById(R.id.spinnerCoordenadas);
        recyclerListaMedidas = (RecyclerView) view.findViewById(R.id.rvListaMedidas);
        recyclerListaMedidas.setLayoutManager(new LinearLayoutManager(getContext()));

        heatMapImg.setMinimum(10);
        heatMapImg.setMaximum(100.0);
        heatMapImg.setRadius(120.0);
        Map<Float, Integer> colors = new ArrayMap<>();
        //build a color gradient in HSV from red at the center to green at the outside
        for (int i = 0; i < 21; i++) {
            float stop = ((float)i) / 20.0f;
            int color = doGradient(i * 5, 0, 100, 0xff00ff00, 0xffff0000);
            colors.put(stop, color);
        }
        heatMapImg.setColorStops(colors);
        spPuntos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showMedidasByCoordenadaId(coordenadas.get(position).getCoordenadaid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        spPuntos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showMedidasByCoordenadaId(coordenadas.get(position).getCoordenadaid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void changeMap(Mapa mapa){
        this.mapa = mapa;
        imgMapaCapturahm.setImageURI(Uri.parse(mapa.getImgMapa().toString()));
        heatMapImg.getWidth();
        heatMapImg.getHeight();
        //heatMapImg.setLayoutParams(new ViewGroup.LayoutParams(imgMapaCapturahm.getLayoutParams()));
        drawable = imgMapaCapturahm.getDrawable();
        ((MainActivity)getActivity()).getAllcoordenadasInMapa(mapa.getMapaid());
    }

    public void cargarMapa(){
        ((MainActivity)getActivity()).cargarHeatMap();
    }

    public void setCoverageFilter(Mapa mapa, List<PointCoverage> pointCoverages){
        int i = 0;
        while(i<pointCoverages.size()){
            int aps =pointCoverages.get(i).getAps();
            Integer x = pointCoverages.get(i).getCoordenada().getPixelx();
            Integer y = pointCoverages.get(i).getCoordenada().getPixely();
            float w = imgMapaCapturahm.getWidth();
            float h = imgMapaCapturahm.getHeight();
            float mw = imgMapaCapturahm.getMeasuredWidth();
            float mh = imgMapaCapturahm.getMeasuredHeight();
            float hmmw = heatMapImg.getWidth();
            float hmmh = heatMapImg.getHeight();
            float xx = x/mw;
            float yy = y/mh;
//            float xx = x/xhm;
//            float yy = y/yhm;
            ca.hss.heatmaplib.HeatMap.DataPoint point = new ca.hss.heatmaplib.HeatMap.DataPoint(xx, yy,aps*10);
            heatMapImg.addData(point);
            heatMapImg.refreshDrawableState();
            heatMapImg.forceRefresh();
            i++;
        }
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


    public void showPointList(List<Coordenada> coordenadas){
        coordenadas = discartCoordenadas(coordenadas);
        this.coordenadas = coordenadas;
        coordenadasString = new ArrayList<>();
        for (Coordenada coor :
                coordenadas) {
            coordenadasString.add(coor.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, coordenadasString);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPuntos.setAdapter(dataAdapter);
    }

    public void showMeasures(List<Medida> medidas){
        this.medidas = medidas;

    }

    public void showMedidasByCoordenadaId(int coorId){
        ((MainActivity)getActivity()).getMedidasByCoordenadaId(coorId);
    }

    public void showMedidasInPoint(List<Medida> medidas){
        this.medidas = medidas;
        medidaAdapter = new MedidaAdapter(medidas,getActivity());
        recyclerListaMedidas.setAdapter(medidaAdapter);
    }

    private List<Coordenada> discartCoordenadas(List<Coordenada> coordenadas){
        int i=0;
        List<Coordenada> coorsf = new ArrayList<>();
        coorsf.add(coordenadas.get(0));
        boolean in = false;
        while (i<coordenadas.size()){
            Coordenada coor = coordenadas.get(i);
            int a=0;
            while (a<coorsf.size()){
                Coordenada aux = coorsf.get(a);
                    if ((aux.getX() == coor.getX()
                            && aux.getY() == coor.getY()
                            && aux.getZ() == coor.getZ())
                            ) {
                        in = true;
                    }
                a++;
            }
            if (!in)coorsf.add(coor);
            in = false;
            i++;
        }
        return coorsf;
    }
}
