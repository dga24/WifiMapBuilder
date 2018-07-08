package com.upc.tfg.WifiMapBuilder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upc.tfg.WifiMapBuilder.R;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Coordenada;
import com.upc.tfg.WifiMapBuilder.model.APSignalStatistics;
import com.upc.tfg.WifiMapBuilder.model.BSSignalStatistics;

import java.util.List;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.CoordenadaViewHolder> {

    private List<Coordenada> coordenadas;
    public PointsAdapter(List<Coordenada> coordenadas){
        this.coordenadas=coordenadas;
    }


    @Override
    public CoordenadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_coordenada,parent,false);
        return new CoordenadaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CoordenadaViewHolder holder, int position) {

        Coordenada coordenada = coordenadas.get(position);
        holder.tvxyz.setText("("+coordenada.getX()+","+coordenada.getY()+","+coordenada.getZ()+")");
    }

    @Override
    public int getItemCount() {
        return coordenadas.size();
    }

    public class  CoordenadaViewHolder extends RecyclerView.ViewHolder{
        private TextView tvxyz;

        public CoordenadaViewHolder(View itemView){
            super(itemView);
            tvxyz = (TextView) itemView.findViewById(R.id.tvxyz);
        }
    }
}
