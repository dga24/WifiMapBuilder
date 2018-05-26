package com.example.garci.positionsystemapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garci.positionsystemapp.R;
import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;

import java.util.List;

public class CargarMapaAdapter extends RecyclerView.Adapter<CargarMapaAdapter.CargarMapaViewHolder> {
    private List<Mapa> mapas;
    public CargarMapaAdapter(List<Mapa> mapas){
        this.mapas = mapas;
    }

    @Override
    public CargarMapaAdapter.CargarMapaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mapa,parent,false);
        return new CargarMapaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CargarMapaViewHolder holder, int position) {
        Mapa mapa = mapas.get(position);
    }

    @Override
    public int getItemCount() {
        return mapas.size();
    }

    public class CargarMapaViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombre;
        private TextView tvEdificio;
        private TextView tvPlanta;
        private ImageView imgMapa;
        private Coordenada coordenadaOrigen;

        public CargarMapaViewHolder(View itemView){
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombreMapa);
            tvEdificio = (TextView) itemView.findViewById(R.id.tvEdificioMapa);
            tvPlanta = (TextView) itemView.findViewById(R.id.tvPlantaMapa);
        }
    }
}
