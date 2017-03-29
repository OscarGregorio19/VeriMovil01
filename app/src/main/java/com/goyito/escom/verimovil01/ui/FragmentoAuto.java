package com.goyito.escom.verimovil01.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyito.escom.verimovil01.R;
import com.goyito.escom.verimovil01.controlador.RecogerAutos;

/**
 * Created by goyito on 18/02/17.
 */

public class FragmentoAuto extends Fragment {

    private static final String INDICE_SECCION
            ="com.goyito.escom.verimovil01.extra.INDICE_SECCION";

    private RecyclerView reciclator;
    private GridLayoutManager layoutManager;
    private AdaptadorMisAutos adaptador;

    /*public static FragmentoAuto nuevaInstancia(int indiceSeccion) {
        FragmentoAuto fragment = new FragmentoAuto();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_misautos, container, false);

        reciclator = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclator.setLayoutManager(layoutManager);


        //reciclator.setAdapter(mBTArrayAdapter); // assign model to view
        //reciclator.setOnClickListener();
                //setOnItemClickListener(mDeviceClickListener);


        adaptador = new AdaptadorMisAutos(RecogerAutos.MISAUTOS);

        reciclator.setAdapter(adaptador);

        return view;
    }
}
