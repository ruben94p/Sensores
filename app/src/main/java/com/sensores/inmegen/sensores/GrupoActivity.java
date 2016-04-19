package com.sensores.inmegen.sensores;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GrupoActivity extends AppCompatActivity {

    private ArrayList<Sensor> sensores;
    private ArrayList<LinearLayout> containers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        sensores = (ArrayList<Sensor>)getIntent().getSerializableExtra("sensores");

        int i = 0;
        for(Sensor sensor : sensores){
            createFragment(sensor,i);
            i++;
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
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.contenedor_grupos);
        for(LinearLayout linearLayout : containers){
            relativeLayout.removeView(linearLayout);
        }
        containers.clear();
    }

    protected void createFragment(final Sensor sensor, int id){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.contenedor_grupos);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setX((id % 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);
        linearLayout.setY((int) (id / 2) * 160 * getResources().getDisplayMetrics().density + 0.5f);

        int layout_id = generateViewId();
        linearLayout.setId(layout_id);

        final GrupoActivity ref = this;
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


    }
}
