package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ruben on 11/03/2016.
 */
public class VerificarSensor {

    public static int verificar(SharedPreferences sharedPreferences, Sensor sensor){
        float min = sharedPreferences.getFloat(sensor.getNombreOpcion() + "_min", 0);
        float max = sharedPreferences.getFloat(sensor.getNombreOpcion() + "_max", 0);

        float va = sensor.getValorActual();

        if(sensor.getNombreOpcion().equals("puerta")){
            return 0;
        }

        if(va < min){
            return -1;
        }
        if(va > max){
            return 1;
        }
        return 0;
    }
}
