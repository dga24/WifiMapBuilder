package com.example.garci.positionsystemapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garci.positionsystemapp.R;
import com.example.garci.positionsystemapp.model.APSignalStatistics;
import com.example.garci.positionsystemapp.model.BSSignalStatistics;

import java.util.List;

public class SeñalAdapter extends RecyclerView.Adapter<SeñalAdapter.SeñalViewHolder> {

    private List<APSignalStatistics> signals;
    public SeñalAdapter(List<APSignalStatistics> signals){
        this.signals=signals;
    }


    @Override
    public SeñalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medida,parent,false);
        return new SeñalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SeñalViewHolder holder, int position) {

        BSSignalStatistics señal = signals.get(position);
        holder.tvSsid.setText(señal.getName());
        holder.tvMac.setText(señal.getAddress());
        holder.tvMedia.setText(String.valueOf(señal.getMean()));
        holder.tvVar.setText(String.valueOf(señal.getVar()));
        holder.tvCalidad.setText(String.valueOf(señal.getQuality()));

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
