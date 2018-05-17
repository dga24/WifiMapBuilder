package com.example.garci.positionsystemapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garci.positionsystemapp.model.Señal;

import java.util.List;

public class SeñalAdapter extends RecyclerView.Adapter<SeñalAdapter.SeñalViewHolder> {

    private List<Señal> signals;
    public SeñalAdapter(List<Señal> signals){
        this.signals=signals;
    }


    @Override
    public SeñalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medida,parent,false);
        return new SeñalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SeñalViewHolder holder, int position) {

        Señal señal = signals.get(position);
        holder.tvSsid.setText(señal.getSsid());
        holder.tvMac.setText(señal.getMac());
        holder.tvMedia.setText(String.valueOf(señal.getMedia()));
        holder.tvVar.setText(String.valueOf(señal.getVar()));
        holder.tvCalidad.setText(String.valueOf(señal.getCalidad()));

    }

    @Override
    public int getItemCount() {
        return signals.size();
    }

    public class  SeñalViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSsid;
        private TextView tvMac;
        private TextView tvMedia;
        private TextView tvVar;
        private TextView tvCalidad;

        public SeñalViewHolder(View itemView){
            super(itemView);
            tvSsid = (TextView) itemView.findViewById(R.id.txtSsid);
            tvMac = (TextView) itemView.findViewById(R.id.txtMac);
            tvMedia = (TextView) itemView.findViewById(R.id.valMedia);
            tvVar = (TextView) itemView.findViewById(R.id.valVar);
            tvCalidad = (TextView) itemView.findViewById(R.id.valCalidad);
        }
    }
}
