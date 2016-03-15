package com.sensores.inmegen.sensores;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Ruben on 11/03/2016.
 */
public class ChecarSensores {

    private final MainActivity activity;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable method;

    public ChecarSensores(final MainActivity activity){
        this.activity = activity;
        method = new Runnable() {
            @Override
            public void run() {
                SensorAsyncTask asyncTask = new SensorAsyncTask(activity);
                asyncTask.execute(Sensor.SENSORES);
                handler.postDelayed(this, 1000 * 60);
            }
        };
    }

    public synchronized void start(){
        method.run();
    }

    public synchronized void stop(){
        handler.removeCallbacks(method);
    }
}
