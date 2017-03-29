package com.goyito.escom.verimovil01.controlador;

import com.goyito.escom.verimovil01.dao.Modelo;

import java.util.List;

/**
 * Created by goyito on 21/02/17.
 */

public class RecogerModelo {
    public static List<Modelo> MODELOS;

    static {
        MODELOS.add(new Modelo(1,"Jetta"));
        MODELOS.add(new Modelo(2,"Stepway"));
    }

    public List<Modelo> devolverModelos() {
        return MODELOS;
    }
}
