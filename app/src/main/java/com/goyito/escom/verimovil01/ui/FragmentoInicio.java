package com.goyito.escom.verimovil01.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goyito.escom.verimovil01.R;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by goyito on 15/02/17.
 */

public class FragmentoInicio extends Fragment {
    public static final String TAG = "FragmentoInicio";
    String t;
    RecyclerView reciclador;

    public FragmentoInicio() {}

    public static FragmentoInicio newInstance(Bundle arguments){
        FragmentoInicio fragmentoInicio = new FragmentoInicio();
        if(arguments != null) {
            fragmentoInicio.setArguments(arguments);
        }
        return fragmentoInicio;
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
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);
        //reciclador = (RecyclerView) view.findViewById(R.id.fragmento_inicio);
        cargarImagen(view);
        saludo(view);

        return view;
    }

    //La vista de layout ha sido creada y ya está disponible
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    /*@Override
    public void onStart(){
        // TextView texto = new TextView("Hola Mundo");
        super.onStart();
        TextView texto = (TextView) getActivity().findViewById(R.id.textUsuarioInicio);

        //Bundle bundle = this.getArguments();
        //t = this.getArguments().getString("texto");

        texto.setText("Hola Cuelos!");
    }*/

    public void cargarImagen(View v) {
        File imgFile = new File("/sdcard/VeriMovil/imagen01.png");
        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imagen = (ImageView) v.findViewById(R.id.imagenPrincipal1);
            imagen.setImageBitmap(myBitmap);
            TextView texto = (TextView) v.findViewById(R.id.textUsuarioInicio);
            texto.setText("Hola buen dia");
            //imagen.getMaxHeight(50);
            //imagen.getMaxWidth(50);
        }
    }

    public void saludo(View v){
        //Date h = new Date();
        Calendar c = new GregorianCalendar();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        //int minuto = c.get(Calendar.MINUTE);
        TextView t = (TextView) v.findViewById(R.id.textUsuarioInicio);
        if(hora>=5 && hora <12){
            t.setText("Buenos dias");
        } else if(hora >=12 && hora<19){
            t.setText("Buenas Tardes");
        } else {
            t.setText("Buenas Noches");
        }
    }
}
