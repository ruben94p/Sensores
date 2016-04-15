package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ruben on 08/03/2016.
 */
public class Sensor implements Serializable {

    public static final Sensor[] SENSORES = {
            new Sensor("system.raspberrypi.temperature_low_one"),
            new Sensor("system.raspberrypi.temperature_A"),
            new Sensor("system.raspberrypi.humidity_A"),
            new Sensor("system.raspberrypi.puerta")
    };

    public static final String IN_SECONDS = "s";
    public static final String IN_MINUTES = "min";
    public static final String IN_HOURS = "h";
    public static final String IN_DAYS = "d";
    public static final String IN_WEEKS = "w";
    public static final String IN_MONTHS = "mon";
    public static final String IN_YEARS = "y";

    private String target;
    private ArrayList<DataPoint> datapoints;
    private boolean isNull;
    private String nombre;
    private boolean notificaciones;

    public Sensor(String target){
        this.target = target;
        this.datapoints = new ArrayList<>();
        this.notificaciones = true;
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

    public boolean isNull() {
        return isNull;
    }

    public String getNombreOpcion(){
        return target.replace("system.raspberrypi.","").toLowerCase();
    }

    public float getValorActual(){
        float valorActual = 0;
        if(datapoints.size() > 0) {
            for(int i=datapoints.size()-1;i>=0;i--) {
                if(!datapoints.get(i).isNull) {
                    isNull = false;
                    valorActual = datapoints.get(i).valor;
                    return valorActual;
                }
            }
            isNull = true;
        }else{
            isNull = true;
        }
        return valorActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static void setNombres(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("opciones", Context.MODE_PRIVATE);
        for(Sensor sensor : SENSORES){
            sensor.setNombre(sharedPreferences.getString(sensor.getTarget(),sensor.getTarget()));
        }
    }

    public boolean daNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public static void setNotificaciones(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("opciones", Context.MODE_PRIVATE);
        for(Sensor sensor : SENSORES){
            sensor.setNotificaciones(sharedPreferences.getBoolean(sensor.getTarget() + "_notificacion",true));
        }
    }
}

