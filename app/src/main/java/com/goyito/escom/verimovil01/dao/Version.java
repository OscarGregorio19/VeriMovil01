package com.goyito.escom.verimovil01.dao;

/**
 * Created by goyito on 21/02/17.
 */

public class Version {
    int idVersion;
    String nombreVersion;

    public Version(int idVersion, String nombreVersion) {
        this.idVersion = idVersion;
        this.nombreVersion = nombreVersion;
    }

    public int getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(int idVersion) {
        this.idVersion = idVersion;
    }

    public String getNombreVersion() {
        return nombreVersion;
    }

    public void setNombreVersion(String nombreVersion) {
        this.nombreVersion = nombreVersion;
    }
}
