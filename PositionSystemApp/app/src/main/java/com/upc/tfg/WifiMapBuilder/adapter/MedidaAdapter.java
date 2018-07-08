package com.upc.tfg.WifiMapBuilder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upc.tfg.WifiMapBuilder.MainActivity;
import com.upc.tfg.WifiMapBuilder.R;
import com.upc.tfg.WifiMapBuilder.dataBase.Entities.Medida;

import java.util.List;

public class MedidaAdapter extends RecyclerView.Adapter<MedidaAdapter.MedidaInfoViewHolder> {

    private Context context;
    private List<Medida> medidas;
    public MedidaAdapter(List<Medida> medidas, Context context){
        this.medidas=medidas;
        this.context = context;
    }


    @Override
    public MedidaInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medidainfo,parent,false);
        return new MedidaInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MedidaInfoViewHolder holder, int position) {

        Medida medida = medidas.get(position);
        holder.tvMedAng.setText(String.valueOf(medida.getAngulo()));
        String aux = medida.getFechaInicio().split("GMT")[0];
        holder.tvDataIni.setText(aux);
        holder.tvMedPeriodo.setText(String.valueOf(medida.getPeriodo()));
        holder.tvMedRep.setText(String.valueOf(medida.getRepeticiones()));
        holder.tvMedNum.setText(String.valueOf(medida.getNumMuestras()));
        holder.tvMedTiempo.setText(String.valueOf(medida.getTiempo()));

    }

    @Override
    public int getItemCount() {
        return medidas.size();
    }

    public class  MedidaInfoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMedAng;
        private TextView tvDataIni;
        private TextView tvMedPeriodo;
        private TextView tvMedRep;
        private TextView tvMedNum;
        private TextView tvMedTiempo;

        public MedidaInfoViewHolder(View itemView){
            super(itemView);
            tvMedAng = (TextView) itemView.findViewById(R.id.tvMedAng);
            tvDataIni = (TextView) itemView.findViewById(R.id.tvDataIni);
            tvMedPeriodo = (TextView) itemView.findViewById(R.id.tvMedPeriodo);
            tvMedRep = (TextView) itemView.findViewById(R.id.tvMedRep);
            tvMedNum = (TextView) itemView.findViewById(R.id.tvMedNum);
            tvMedTiempo = (TextView) itemView.findViewById(R.id.tvMedTiempo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ((MainActivity)context).showDetailsMedida(medidas.get(position).getMedidaid());
                }
            });
        }
    }
}
