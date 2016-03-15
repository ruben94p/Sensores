package com.sensores.inmegen.sensores;

import java.util.ArrayList;

/**
 * Created by Ruben on 08/03/2016.
 */
public class Sensor {

    public static Sensor[] SENSORES = {
            new Sensor("system.raspberrypi.temperature_low_one"),
            new Sensor("system.raspberrypi.temperature_A"),
            new Sensor("system.raspberrypi.humidity_A"),
            new Sensor("system.raspberrypi.puerta")
    };

    private String target;
    private ArrayList<DataPoint> datapoints;

    public Sensor(String target){
        this.target = target;
        this.datapoints = new ArrayList<>();
    }

    public String getTarget() {
        return target;
    }

    public ArrayList<DataPoint> getDatapoints() {
        return datapoints;
    }

    public void setDatapoints(ArrayList<DataPoint> datapoints) {
        this.datapoints = datapoints;
    }

    public String getNombreOpcion(){
        return target.replace("system.raspberrypi.","").toLowerCase();
    }

    public float getValorActual(){
        float valorActual = 0;
        if(datapoints.size() > 0) {
            valorActual  = datapoints.get(datapoints.size() - 1).valor;
        }
        return valorActual;
    }
}

