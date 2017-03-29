package com.goyito.escom.verimovil01.dao;

/**
 * Created by goyito on 21/02/17.
 */

public class Anio {
    int idAnio;
    int anio;

    public int getIdAnio() {
        return idAnio;
    }

    public void setIdAnio(int idAnio) {
        this.idAnio = idAnio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Anio(int idAnio, int anio) {

        this.idAnio = idAnio;
        this.anio = anio;
    }
}
