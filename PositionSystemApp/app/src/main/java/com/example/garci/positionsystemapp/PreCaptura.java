package com.example.garci.positionsystemapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreCaptura.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreCaptura extends Fragment {


    private OnFragmentInteractionListener mListener;

    EditText txtX;
    EditText txtY;
    EditText txtZ;
    Spinner editAng;
    Button btnSelect;

    EditText editMuestras;
    EditText editPeriodo;
    EditText editRepeticiones;
    EditText editTiempo;
    Button btnStart;
    ImageButton refreshMap;

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
        editAng = (Spinner) view.findViewById(R.id.editAng);
        btnSelect = (Button) view.findViewById(R.id.btnSelect);

        editMuestras = (EditText) view.findViewById(R.id.editMuestras);
        editPeriodo = (EditText) view.findViewById(R.id.editPeriodo);
        editRepeticiones = (EditText) view.findViewById(R.id.editRepeticiones);
        editTiempo = (EditText) view.findViewById(R.id.editTiempo);
        btnStart = (Button) view.findViewById(R.id.btnStart);

        refreshMap = (ImageButton) view.findViewById(R.id.btnRefreshImagen);

        refreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
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

    public void openMap(){
        dispatchTakePictureIntent();

    }
    final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView mImageView;
    private void dispatchTakePictureIntent(){
        System.out.print("\n\n\n\ncrearIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

//    String mCurrentPhotoPath;
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

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
