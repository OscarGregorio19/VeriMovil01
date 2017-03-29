package com.goyito.escom.verimovil01.controlador;

import com.goyito.escom.verimovil01.dao.Anio;

import java.util.List;

/**
 * Created by goyito on 21/02/17.
 */

public class RecogerAnio {
    public static List<Anio> ANIO;

    static {
        ANIO.add(new Anio(1,2015));
        ANIO.add(new Anio(2,2016));
    }
}
