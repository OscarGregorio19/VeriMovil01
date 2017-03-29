package com.goyito.escom.verimovil01.dao;

/**
 * Created by goyito on 20/02/17.
 */

public class Marcas {
    int idMarca;
    String nombreMarca;

    public Marcas(int idMarca, String nombreMarca) {
        this.idMarca = idMarca;
        this.nombreMarca = nombreMarca;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    @Override
    public String toString() {
        return nombreMarca;
    }
}
