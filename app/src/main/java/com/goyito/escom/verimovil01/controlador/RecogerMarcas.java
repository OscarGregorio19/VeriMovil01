package com.goyito.escom.verimovil01.controlador;

import com.goyito.escom.verimovil01.dao.Marcas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goyito on 20/02/17.
 */

public class RecogerMarcas {
    public static final List<Marcas> MARCAS = new ArrayList<>();

    static {
        MARCAS.add(new Marcas(1,"VW"));
        MARCAS.add(new Marcas(2,"Renault"));
    }

    public List<Marcas> devolverMarcas() {
        return  MARCAS;
    }
}
