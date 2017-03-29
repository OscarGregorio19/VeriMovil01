package com.goyito.escom.verimovil01.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.goyito.escom.verimovil01.R;
import com.goyito.escom.verimovil01.controlador.RecogerMarcas;
import com.goyito.escom.verimovil01.dao.Marcas;
import com.goyito.escom.verimovil01.dao.Modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by goyito on 20/02/17.
 */

public class FragmentoCatalogo extends Fragment {
    public static final String TAG = "FragmentoCatalogo";
    int idMarca;

    /*public FragmentoCatalogo(List<Marcas> marcas){
        this.marcas = marcas;
    }*/

    public static FragmentoCatalogo newInstance(Bundle arguments){
        FragmentoCatalogo fragmentoCatalogo = new FragmentoCatalogo();
        if(arguments != null) {
            fragmentoCatalogo.setArguments(arguments);
        }
        return fragmentoCatalogo;
    }

    //El fragment se ha adjuntado al Activity
    //@Override
    public void onAttach(ActividadPrincipal activity) {
        super.onAttach(activity);
    }

    //El Fragment ha sido creado
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //El Fragment va a cargar su layout, el cual debemos especificar
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmento_catalogo,container,false);
        llenarSpinnerMarcas(view);


        return view;
    }

    public void llenarSpinnerMarcas(View v){
        List<Marcas> marcasList; List<Modelo> modeloList;
        List<String> lista = new ArrayList<String>();
        Spinner marcas = (Spinner) v.findViewById(R.id.marcas_spinner);
        /*Spinner modelo = (Spinner) v.findViewById(R.id.modelo_spinner);
        Spinner version = (Spinner) v.findViewById(R.id.version_spinner);
        Spinner anio = (Spinner) v.findViewById(R.id.anio_spinner);*/
        marcasList = new RecogerMarcas().devolverMarcas();

        LinkedList unalista = new LinkedList();
        for (int i=0; i<marcasList.size(); i++){
            //lista.add(marcasList.get(i).getNombreMarca());
            unalista.add(marcasList.get(i));
        }
        //marcas.setOnItemSelectedListener(this);
        ArrayAdapter spinner = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,unalista);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marcas.setOnItemSelectedListener(new MyOnItemSelectedListener());
        marcas.setAdapter(spinner);
    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView parent, View view, int pos,long id) {
            cargarSpinnerModelos(parent.getSelectedItemPosition(), view);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    public void cargarSpinnerModelos(int i, View v) {
        Spinner modelo = (Spinner) v.findViewById(R.id.modelo_spinner);

    }

    //La vista de layout ha sido creada y ya está disponible
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //llenarSpinnerMarcas(view);
    }

    //La vista ha sido creada y cualquier configuración guardada está cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //El Activity que contiene el Fragment ha terminado su creación
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
