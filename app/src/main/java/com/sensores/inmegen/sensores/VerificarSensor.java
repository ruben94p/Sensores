package com.sensores.inmegen.sensores;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by Ruben on 11/03/2016.
 */
public class VerificarSensor {

    public static int verificar(SharedPreferences sharedPreferences, Sensor sensor){
        float min = sharedPreferences.getFloat(sensor.getNombreOpcion() + "_min", 0);
        float max = sharedPreferences.getFloat(sensor.getNombreOpcion() + "_max", 0);

        float ref = ((min + max) / 2) - min;

        float va = sensor.getValorActual();

        if(sensor.getNombreOpcion().equals("puerta")){

            float contador = 0;
            for(float i=0;i<max && i<sensor.getDatapoints().size();i++){
                float p = sensor.getDatapoints().get(sensor.getDatapoints().size()-(int)i-1).valor;
                if(p == 0){
                    contador = 0;
                }else{
                    contador++;
                }
            }

            if(contador < min){
                return 0;
            }
            if(contador < max){
                return 1;
            }
            return 2;
        }

        //Alerta
        if(va < min + ref && va > min){
            return -1;
        }

        if(va > max - ref && va < max){
            return 1;
        }

        //Alerta grave
        if(va < min){
            return -2;
        }
        if(va > max){
            return 2;
        }

        //Normal
        return 0;
    }
}
