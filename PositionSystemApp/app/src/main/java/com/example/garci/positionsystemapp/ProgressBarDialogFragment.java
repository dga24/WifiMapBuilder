package com.example.garci.positionsystemapp;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressBarDialogFragment extends DialogFragment {


    ProgressBar progressBar;

    public ProgressBarDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_bar_dialog, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.miProgressBarBD);

        return view;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
