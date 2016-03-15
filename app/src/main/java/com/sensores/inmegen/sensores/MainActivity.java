package com.sensores.inmegen.sensores;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private ChecarSensores checarSensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checarSensores = new ChecarSensores(this);

        inicializarOpciones();


        /*if(!serviceRunning()){
            startService(new Intent(this.getApplicationContext(), Servicio.class));
        }*/
    }

    private void inicializarOpciones(){
        SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("temperature_low_one_min")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("temperature_low_one_min", -26.0f);
            editor.putFloat("temperature_low_one_max", -20.0f);
            editor.putFloat("temperature_a_min", 19.0f);
            editor.putFloat("temperature_a_max", 24.0f);
            editor.putFloat("humidity_a_min", 18.0f);
            editor.putFloat("humidity_a_max", 23.0f);

            editor.putFloat("puerta_min", 2.0f);
            editor.putFloat("puerta_max", 4.0f);
            editor.commit();
        }

    }

    protected ListView getListView(){
        return (ListView)findViewById(R.id.lista);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.definir:
                Intent i = new Intent(this, OpcionesActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checarSensores.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checarSensores.stop();
    }

    public boolean serviceRunning(){
        return isMyServiceRunning(Servicio.class);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
