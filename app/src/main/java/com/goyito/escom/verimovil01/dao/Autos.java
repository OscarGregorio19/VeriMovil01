package com.goyito.escom.verimovil01.dao;

/**
 * Created by goyito on 18/02/17.
 */

public class Autos {
    private int id;
    private String marca;
    private String modelo;
    private String version;
    private int ano;
    private int idDrawable;

    public Autos(int id, String marca, String modelo, String version, int ano, int idDrawable) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.version = version;
        this.ano = ano;
        this.idDrawable = idDrawable;
    }


    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getVersion() {
        return version;
    }

    public int getAno() {
        return ano;
    }

    public int getIdDrawable() {

        return idDrawable;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        modelo = modelo;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public int getId() {
        return id;
    }
}
