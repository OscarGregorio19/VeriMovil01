package com.goyito.escom.verimovil01.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goyito.escom.verimovil01.R;
import com.goyito.escom.verimovil01.dao.Autos;

import java.security.Principal;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

/**
 * Created by goyito on 15/02/17.
 */

public class AdaptadorMisAutos
        extends RecyclerView.Adapter<AdaptadorMisAutos.ViewHolder> {

    public final List<Autos> items;
    private Context context = null;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private String mItem;
        // Campos respectivos de un item
        public TextView id;
        public TextView marca;
        public TextView modelo;
        public TextView version;
        public TextView ano;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            //itemView.setOnClickListener(this);
            v.setOnClickListener(this);
            id = (TextView) v.findViewById(R.id.id_user);
            marca = (TextView) v.findViewById(R.id.marca_auto);
            modelo = (TextView) v.findViewById(R.id.modelo_auto);
            version = (TextView) v.findViewById(R.id.version_auto);
            ano = (TextView) v.findViewById(R.id.ano_modelo);
            imagen = (ImageView) v.findViewById(R.id.miniatura_misautos);

            context = v.getContext();
        }

        @Override
        public void onClick(View view) {
            int id = items.get(getPosition()).getId(); //recupero el id del carro seleccionado
            Toast.makeText(view.getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
            abrirActivity(id);
        }
    }

    public void abrirActivity(int id) {
        Intent bluetooth = new Intent(context.getApplicationContext(), ActividadBluetooth.class);
        //principal.putExtra("texto",usuario);
            context.startActivity(bluetooth);
    }

    public AdaptadorMisAutos(List<Autos> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_misautos, null); //pongo null y con eso se acomodan las colomnas sin espcio

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Autos item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.id.setText("Id: "+item.getId());
        viewHolder.marca.setText("Marca: "+item.getMarca());
        viewHolder.modelo.setText("Modelo: "+item.getModelo());
        viewHolder.version.setText("Version: "+item.getVersion());
        viewHolder.ano.setText("Año:" + item.getAno());
    }
}
