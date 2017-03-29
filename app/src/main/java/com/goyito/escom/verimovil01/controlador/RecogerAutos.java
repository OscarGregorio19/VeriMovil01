package com.goyito.escom.verimovil01.controlador;

import com.goyito.escom.verimovil01.dao.Autos;
import com.goyito.escom.verimovil01.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by goyito on 18/02/17.
 */

public class RecogerAutos {
    public static final List<Autos> MISAUTOS = new ArrayList<>();

    static {
        MISAUTOS.add(new Autos(216,"VW","Jetta","Clasico",2015,R.drawable.automovil));
        MISAUTOS.add(new Autos(357,"Renault","Sandero",null,2017,R.drawable.automovil));
        MISAUTOS.add(new Autos(120,"Seat","Ibiza","5D",2017,R.drawable.automovil));
    }

}
