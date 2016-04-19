package com.sensores.inmegen.sensores;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private ChecarSensores checarSensores;

    private ArrayList<LinearLayout> containers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sensor.setNombres(this);
        Sensor.setNotificaciones(this);
        Sensor.setGrupos(this);
        Grupo.setGrupos(this);

        checarSensores = new ChecarSensores(this);

        inicializarOpciones();
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

    private int generateViewId() {

        final AtomicInteger sNextGeneratedId = new AtomicInteger(1000);
        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }

    protected void removeFragments(){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.container);
        for(LinearLayout linearLayout : containers){
            relativeLayout.removeView(linearLayout);
        }
        containers.clear();
    }

    /*protected void createFragment(final Sensor sensor, int id){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.container);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setX((id % 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);
        linearLayout.setY((int) (id / 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);

        int layout_id = generateViewId();
        linearLayout.setId(layout_id);

        final MainActivity ref = this;
        if(!sensor.getTarget().equals("system.raspberrypi.puerta")) {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ref, GraficaActivity.class);
                    i.putExtra("sensor", sensor);
                    startActivity(i);
                }
            });
        }

        getSupportFragmentManager().beginTransaction().add(layout_id, SensorFragment.newInstance(sensor)).commitAllowingStateLoss();

        containers.add(linearLayout);
        relativeLayout.addView(linearLayout);


    }*/

    protected void createFragment(final ArrayList<Sensor> sensores, final String grupo, int id){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.container);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setX((id % 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);
        linearLayout.setY((int) (id / 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);

        int layout_id = generateViewId();
        linearLayout.setId(layout_id);

        final MainActivity ref = this;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ref, GrupoActivity.class);
                i.putExtra("sensores", sensores);
                i.putExtra("grupo", grupo);
                startActivity(i);
            }
        });

        getSupportFragmentManager().beginTransaction().add(layout_id, GrupoFragment.newInstance(sensores, grupo)).commitAllowingStateLoss();

        containers.add(linearLayout);
        relativeLayout.addView(linearLayout);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent i;
        switch (item.getItemId()) {
            case R.id.definir:
                i = new Intent(this, OpcionesActivity.class);
                startActivity(i);
                break;
            case R.id.definirGrupos:
                i = new Intent(this, OpcionesGrupoActivity.class);
                startActivity(i);
                break;
            case R.id.administarGrupos:
                i = new Intent(this, ListGrupoActivity.class);
                startActivity(i);
                break;
            case R.id.ayuda:
                i = new Intent(this, AyudaActivity.class);
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

        stopService(new Intent(this, Servicio.class));

        checarSensores.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeFragments();
        startService(new Intent(this, Servicio.class));
        checarSensores.stop();
    }


}
