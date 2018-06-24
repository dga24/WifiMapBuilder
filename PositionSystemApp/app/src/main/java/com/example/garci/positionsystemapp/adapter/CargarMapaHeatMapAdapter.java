package com.example.garci.positionsystemapp.adapter;

        import android.app.Activity;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.app.Fragment;
        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.v4.app.FragmentManager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.garci.positionsystemapp.HeatMap;
        import com.example.garci.positionsystemapp.MainActivity;
        import com.example.garci.positionsystemapp.PreCaptura;
        import com.example.garci.positionsystemapp.R;
        import com.example.garci.positionsystemapp.dataBase.Entities.Coordenada;
        import com.example.garci.positionsystemapp.dataBase.Entities.Mapa;

        import java.util.List;

public class CargarMapaHeatMapAdapter extends RecyclerView.Adapter<CargarMapaHeatMapAdapter.CargarMapaViewHolder> {

    private List<Mapa> mapas;
    public CargarMapaHeatMapAdapter(List<Mapa> mapas){
        this.mapas = mapas;
    }

    int mapaSelectedid;
    int posSelected;

    Context context;

    Dialog dialog;

    FragmentManager fragmentManager;

    Activity activity;

    @Override
    public CargarMapaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mapa,parent,false);
        final CargarMapaViewHolder cargarMapaViewHolder = new CargarMapaViewHolder(v);

        return cargarMapaViewHolder;
    }

    @Override
    public void onBindViewHolder(CargarMapaViewHolder holder, int position) {
        Mapa mapa = mapas.get(position);
        holder.tvNombre.setText(mapa.getNombre());
        holder.tvEdificio.setText(mapa.getEdificio());
        holder.tvPlanta.setText(String.valueOf(mapa.getPlanta()));
        holder.imgMapa.setImageURI(Uri.parse(mapa.getImgMapa()));

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
        private ConstraintLayout vMapaItem;

        public CargarMapaViewHolder(View itemView){
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombreMapaOld);
            tvEdificio = (TextView) itemView.findViewById(R.id.tvEdificioMapaOld);
            tvPlanta = (TextView) itemView.findViewById(R.id.tvPlantaMapaOld);
            imgMapa = (ImageView) itemView.findViewById(R.id.ivImgMapaOld);
            vMapaItem = (ConstraintLayout) itemView.findViewById(R.id.vMapaItem);
            vMapaItem.setClickable(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posSelected = getAdapterPosition();
                    mapaSelectedid = mapas.get(posSelected).getMapaid();
                    notifyDataSetChanged();
                    newHeatMap(mapaSelectedid);
                }
            });
        }



    }

    public void newHeatMap(int mapaid) {
        HeatMap heatMap = new HeatMap();
        Bundle args = new Bundle();
        args.putString("action","comprovarMapa");
        args.putInt("mapaid", mapaid);
        heatMap.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_main,heatMap).commit();
        //fragmentManager.beginTransaction().add(preCaptura,"precaptura").commit();
        dialog.dismiss();
    }

    public  void setContext(Context context){
        this.context =context;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setDialog(Dialog dialog){
        this.dialog=dialog;
    }
}
