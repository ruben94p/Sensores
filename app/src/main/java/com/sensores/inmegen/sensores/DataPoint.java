package com.sensores.inmegen.sensores;

/**
 * Created by Ruben on 08/03/2016.
 */
public class DataPoint {

    public long tiempo;
    public float valor;

    public DataPoint(float valor, long tiempo) {
        this.valor = valor;
        this.tiempo = tiempo;
    }
}
