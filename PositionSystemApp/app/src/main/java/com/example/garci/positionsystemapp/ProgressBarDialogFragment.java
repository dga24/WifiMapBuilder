package com.example.garci.positionsystemapp;


import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
    public class ProgressBarDialogFragment extends DialogFragment {
        int mNum;
        ProgressBar progressBar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_progress_bar_dialog, container, false);
            progressBar = (ProgressBar) v.findViewById(R.id.miProgressBarBD);
            return v;
        }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
