
package com.upc.tfg.WifiMapBuilder;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.upc.tfg.WifiMapBuilder.adapter.SeñalAdapter;
import com.upc.tfg.WifiMapBuilder.model.APSignalStatistics;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsDialogFragment extends DialogFragment {

    //private List<APSignalStatistics> signals;
    private RecyclerView recyclerListaSignals;
    private SeñalAdapter adaptador;
    private Button btnAceptarResults;


    public ResultsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results_dialog, container, false);

        //signals = new ArrayList<>();
        recyclerListaSignals = (RecyclerView) view.findViewById(R.id.rvListaSignalsPostCaptura);
        recyclerListaSignals.setLayoutManager(new LinearLayoutManager(getContext()));
        btnAceptarResults = (Button) view.findViewById(R.id.btnAceptarResult);

        btnAceptarResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }




    public void inicializarAdaptador(List<APSignalStatistics> signals){
        adaptador = new SeñalAdapter(signals);
        recyclerListaSignals.setAdapter(adaptador);
    }

}
