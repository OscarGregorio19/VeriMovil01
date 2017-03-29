package com.goyito.escom.verimovil01.dao;

/**
 * Created by goyito on 21/02/17.
 */

public class Modelo {
    int idModelo;
    String nombreModelo;

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public int getIdModelo() {

        return idModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public Modelo(int idModelo, String nombreModelo) {

        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
    }
}
