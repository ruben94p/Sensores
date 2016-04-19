package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class OpcionesGrupoActivity extends AppCompatActivity {

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;

    private TextView sensor1;
    private TextView sensor2;
    private TextView sensor3;
    private TextView sensor4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_grupo);

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);

        sensor1 = (TextView)findViewById(R.id.sensor1);
        sensor2 = (TextView)findViewById(R.id.sensor2);
        sensor3 = (TextView)findViewById(R.id.sensor3);
        sensor4 = (TextView)findViewById(R.id.sensor4);

        sensor1.setText(Sensor.SENSORES[0].getNombre());
        sensor2.setText(Sensor.SENSORES[1].getNombre());
        sensor3.setText(Sensor.SENSORES[2].getNombre());
        sensor4.setText(Sensor.SENSORES[3].getNombre());


        GrupoAdapter adapter = new GrupoAdapter(this,Grupo.GRUPOS);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);

        spinner1.setSelection(Grupo.GRUPOS.indexOf(Grupo.getGrupoByNombre(Sensor.SENSORES[0].getGrupo().getNombre())));
        spinner2.setSelection(Grupo.GRUPOS.indexOf(Grupo.getGrupoByNombre(Sensor.SENSORES[1].getGrupo().getNombre())));
        spinner3.setSelection(Grupo.GRUPOS.indexOf(Grupo.getGrupoByNombre(Sensor.SENSORES[2].getGrupo().getNombre())));
        spinner4.setSelection(Grupo.GRUPOS.indexOf(Grupo.getGrupoByNombre(Sensor.SENSORES[3].getGrupo().getNombre())));

    }

    public void guardarGrupos(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Sensor.SENSORES[0].getTarget() + "_grupo",((Grupo)spinner1.getSelectedItem()).getNombre());
        editor.putString(Sensor.SENSORES[1].getTarget() + "_grupo",((Grupo)spinner2.getSelectedItem()).getNombre());
        editor.putString(Sensor.SENSORES[2].getTarget() + "_grupo",((Grupo)spinner3.getSelectedItem()).getNombre());
        editor.putString(Sensor.SENSORES[3].getTarget() + "_grupo",((Grupo)spinner4.getSelectedItem()).getNombre());
        editor.commit();

        Sensor.setGrupos(this);

        finish();
    }
}
