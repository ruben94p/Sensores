package com.sensores.inmegen.sensores;

import java.io.Serializable;

/**
 * Created by Ruben on 08/03/2016.
 */
public class DataPoint implements Serializable {

    public long tiempo;
    public float valor;
    public boolean isNull = false;

    public DataPoint(float valor, long tiempo) {
        this.valor = valor;
        this.tiempo = tiempo;
    }

    public DataPoint(float valor, long tiempo, boolean isNull) {
        this.valor = valor;
        this.tiempo = tiempo;
        this.isNull = isNull;
    }
}
