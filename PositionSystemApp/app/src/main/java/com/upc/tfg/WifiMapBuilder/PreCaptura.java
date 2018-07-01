package com.upc.tfg.WifiMapBuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import com.upc.tfg.WifiMapBuilder.dataBase.AppRoomDatabase;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Mapa;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;
import com.upc.tfg.WifiMapBuilder.model.Manager;
import com.upc.tfg.WifiMapBuilder.model.Parameters;


import java.util.Date;

import static android.graphics.Paint.Style.STROKE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreCaptura.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreCaptura extends Fragment {


    private OnFragmentInteractionListener mListener;

    Manager manager;
    AppRoomDatabase db;
    Mapa mapa;
    Coordenada coordenada;
    int angle;
    int pixelX = 0;
    int pixelY = 0;


    AbrirMapaDialog abrirMapa;

    EditText txtX;
    EditText txtY;
    EditText txtZ;
    EditText txtAng;
    Button btnSelect;
    ImageView imgMapaCaptura;
    Button btnSelectPreCaptura;

    TextView tvPixelX;
    TextView tvPixelY;

    EditText editMuestras;
    EditText editPeriodo;
    EditText editRepeticiones;
    EditText editTiempo;
    Button btnStartCaptura;
    ImageButton refreshMap;

    TextView tvWifiScan;
    Button btnRefreshScan;

    SimpleScanWifi simpleScanWifi;

    Canvas canvas;

    Paint paint;

    boolean haveCoor0 = true;

    ConstraintLayout newMapaParam;
    ConstraintLayout paramPreCaptura;

    AcceptNewMap acceptNewMap;
    CargarMapaDialogFragment cargarMapaDialogFragment;

    TextView tvParamPixelX;
    TextView tvParamPixelY;
    Button btnguardarCoordenadaOrigen;

    Medida medida;

    boolean existmapa = true;



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
        txtAng = (EditText) view.findViewById(R.id.editAng);
        imgMapaCaptura = (ImageView) view.findViewById(R.id.imgMapaCaptura);
        btnSelectPreCaptura = (Button) view.findViewById(R.id.btnSelectPreCaptura);

        tvPixelX = (TextView) view.findViewById(R.id.tvPixelX);
        tvPixelY = (TextView) view.findViewById(R.id.tvPixelY);

        editMuestras = (EditText) view.findViewById(R.id.editMuestras);
        editPeriodo = (EditText) view.findViewById(R.id.editPeriodo);
        editRepeticiones = (EditText) view.findViewById(R.id.editRepeticiones);
        editTiempo = (EditText) view.findViewById(R.id.editTiempo);
        btnStartCaptura = (Button) view.findViewById(R.id.btnStartCaptura);
        btnStartCaptura.setEnabled(false);

        tvWifiScan = (TextView) view.findViewById(R.id.tvWifiScan);
        btnRefreshScan = (Button) view.findViewById(R.id.btnRefreshScan);

        newMapaParam = (ConstraintLayout) view.findViewById(R.id.newMapaParam);
        paramPreCaptura = (ConstraintLayout) view.findViewById(R.id.contentParam);

        refreshMap = (ImageButton) view.findViewById(R.id.btnRefreshImagen);

        simpleScanWifi = new SimpleScanWifi(getActivity());

        final boolean onStart = true;


        btnguardarCoordenadaOrigen = (Button) view.findViewById(R.id.btnguardarCoordenadaOrigen);
        tvParamPixelX = (TextView) view.findViewById(R.id.tvParamPixelX);
        tvParamPixelY = (TextView) view.findViewById(R.id.tvParamPixelY);


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

        //loadMapa();
//        }if(mapa!=null){
//            changeMap(mapa);
//        }else{
//            cargarMapa();
//        }



        btnSelectPreCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtX.getText().toString().equals("")&&!txtY.getText().toString().equals("")&&!txtZ.getText().toString().equals("")
                        &&!txtAng.getText().toString().equals("")
                        &&pixelX!=0&&pixelY!=0) {
                    coordenada = new Coordenada(mapa.getMapaid(), Double.parseDouble(txtX.getText().toString()),
                            Double.parseDouble(txtY.getText().toString()),
                            Double.parseDouble(txtZ.getText().toString()),
                            pixelX, pixelY);
                    angle = Integer.parseInt(txtAng.getText().toString());
                    txtX.setEnabled(false);
                    txtY.setEnabled(false);
                    txtZ.setEnabled(false);
                    txtAng.setEnabled(false);
                    tvPixelX.setTextSize(18);
                    tvPixelX.setTextColor(Color.GREEN);
                    tvPixelY.setTextColor(Color.GREEN);
                    tvPixelY.setTextSize(18);
                    btnStartCaptura.setEnabled(true);
                }
            }
        });


        simpleScanWifi = new SimpleScanWifi(getActivity());
        simpleScanWifi.execute();
        btnRefreshScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleScanWifi.execute();
            }
        });

        refreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMapa();
            }
        });
        paint = new Paint(paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.FILL);



        final Bitmap bitmap = ((BitmapDrawable)imgMapaCaptura.getDrawable()).getBitmap();
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        canvas = new Canvas(mutableBitmap);

        btnStartCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCaptura();
            }
        });


        imgMapaCaptura.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        pixelX = (int)event.getRawX();
                        pixelY = (int)event.getRawY();
                        //int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());
                        tvPixelX.setText(String.valueOf(pixelX));
                        tvPixelY.setText(String.valueOf(pixelY));
                        tvParamPixelX.setText(String.valueOf(pixelX));
                        tvParamPixelY.setText(String.valueOf(pixelY));
                        canvas.drawBitmap(bitmap, 0, 0, paint);
                        canvas.drawCircle(pixelX, pixelY,100, paint);
                }
                return true;
            }
        });



//        imgMapaCaptura.setOnTouchListener(new View.OnTouchListener(){
//            final Bitmap bitmap = ((BitmapDrawable)imgMapaCaptura.getDrawable()).getBitmap();
//            @Override
//            public boolean onTouch(View v, MotionEvent event){
//                pixelX = (int)event.getRawX();
//                pixelY = (int)event.getRawY();
//                //int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());
//                tvPixelX.setText(String.valueOf(pixelX));
//                tvPixelY.setText(String.valueOf(pixelY));
//                tvParamPixelX.setText(String.valueOf(pixelX));
//                tvParamPixelY.setText(String.valueOf(pixelY));
//                canvas.drawBitmap(bitmap, 0, 0, paint);
//                canvas.drawCircle(pixelX, pixelY,100, paint);
//                return true;
//            }
//        });

        btnguardarCoordenadaOrigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenada = new Coordenada(mapa.getMapaid(), 0,0,0, pixelX, pixelY);
                ((MainActivity)getActivity()).createCoorOrigen(mapa,coordenada);
            }
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public void startCaptura(){
        Parameters parameters = new Parameters(Double.parseDouble(editPeriodo.getText().toString()),
                                Integer.valueOf(editRepeticiones.getText().toString()),
                                Double.parseDouble(editTiempo.getText().toString()),
                                Integer.valueOf(editMuestras.getText().toString()));
        medida = new Medida(null,angle,mapa.getMapaid(), new Date().toString(),null,
                parameters.getPeriod(),parameters.getRep(),parameters.getTemp(),parameters.getNumSample());
        ((MainActivity)getActivity()).startScan(parameters, medida, coordenada, angle);
    }

    public void showAcceptNewMap(Mapa mapa){
        FragmentManager fm = getFragmentManager();
        acceptNewMap = new AcceptNewMap();
        acceptNewMap.setMapa(mapa);
        acceptNewMap.show(getActivity().getSupportFragmentManager(),"Fdialog");
//        Bundle args= new Bundle();
//        args.putString("name", mapa.getNombre());
//        args.putString("building", mapa.getEdificio());
//        args.putInt("floor", mapa.getPlanta());
//        args.putString("imgUriString", mapa.getImgMapa().toString());
//        acceptNewMap.setArguments(args);

//        acceptNewMap.show(getFragmentManager(),"acceptnewmapFragment");
    }

    public void loadMapa(){

    }

    public void  cargarMapa(){
            ((MainActivity) getActivity()).cargarMapa();
    }


    public void newMapWithoutCoor(Mapa mapa){
        haveCoor0=false;
        txtX.setText(String.valueOf(0));
        txtY.setText(String.valueOf(0));
        txtX.setEnabled(false);
        txtY.setEnabled(false);
        paramPreCaptura.setVisibility(View.GONE);
        newMapaParam.setVisibility(View.VISIBLE);
        btnSelectPreCaptura.setVisibility(View.GONE);
    }

    public void newMapComplete(Mapa mapa){
        haveCoor0=true;
        paramPreCaptura.setVisibility(View.VISIBLE);
        newMapaParam.setVisibility(View.GONE);
        btnSelectPreCaptura.setVisibility(View.VISIBLE);
        txtX.setEnabled(true);
        txtY.setEnabled(true);
    }

    public void changeMap(Mapa mapa){
        this.mapa = mapa;
        imgMapaCaptura.setImageURI(Uri.parse(mapa.getImgMapa()));
        if(mapa.getCoordenadaid()==null){
           newMapWithoutCoor(mapa);
        }
        else{
            newMapComplete(mapa);
        }
    }
}
