package com.goyito.escom.verimovil01.controlador;

import com.goyito.escom.verimovil01.dao.Version;

import java.util.List;

/**
 * Created by goyito on 21/02/17.
 */

public class RecogerVersion {
    public static List<Version> VERSION;

    static {
        VERSION.add(new Version(1,"Clasico"));
        VERSION.add(new Version(2,""));
    }

    public static List<Version> devolverVersion() {
        return VERSION;
    }
}
