package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class OpcionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        EditText url = (EditText)findViewById(R.id.url);

        EditText low_one_min = (EditText)findViewById(R.id.low_one_min);
        EditText low_one_max = (EditText)findViewById(R.id.low_one_max);
        EditText temperature_a_min = (EditText)findViewById(R.id.temperature_a_min);
        EditText temperature_a_max = (EditText)findViewById(R.id.temperature_a_max);
        EditText humidity_a_min = (EditText)findViewById(R.id.humidity_a_min);
        EditText humidity_a_max = (EditText)findViewById(R.id.humidity_a_max);
        EditText puerta_min = (EditText)findViewById(R.id.puerta_min);
        EditText puerta_max = (EditText)findViewById(R.id.puerta_max);

        EditText temperature_low_one = (EditText)findViewById(R.id.temperature_low_one);
        EditText temperature_a = (EditText)findViewById(R.id.temperature_A);
        EditText humidity_a = (EditText)findViewById(R.id.humidity_A);
        EditText puerta = (EditText)findViewById(R.id.puerta);

        CheckBox not1 = (CheckBox)findViewById(R.id.not1);
        CheckBox not2 = (CheckBox)findViewById(R.id.not2);
        CheckBox not3 = (CheckBox)findViewById(R.id.not3);
        CheckBox not4 = (CheckBox)findViewById(R.id.not4);

        SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);

        temperature_low_one.setText(sharedPreferences.getString(Sensor.SENSORES[0].getTarget(),Sensor.SENSORES[0].getTarget()));
        temperature_a.setText(sharedPreferences.getString(Sensor.SENSORES[1].getTarget(),Sensor.SENSORES[1].getTarget()));
        humidity_a.setText(sharedPreferences.getString(Sensor.SENSORES[2].getTarget(),Sensor.SENSORES[2].getTarget()));
        puerta.setText(sharedPreferences.getString(Sensor.SENSORES[3].getTarget(),Sensor.SENSORES[3].getTarget()));

        url.setText(sharedPreferences.getString("url","http://192.168.52.50").replaceAll("/$",""));

        low_one_min.setText(Float.toString(sharedPreferences.getFloat("temperature_low_one_min",0)));
        low_one_max.setText(Float.toString(sharedPreferences.getFloat("temperature_low_one_max",0)));
        temperature_a_min.setText(Float.toString(sharedPreferences.getFloat("temperature_a_min",0)));
        temperature_a_max.setText(Float.toString(sharedPreferences.getFloat("temperature_a_max", 0)));
        humidity_a_min.setText(Float.toString(sharedPreferences.getFloat("humidity_a_min",0)));
        humidity_a_max.setText(Float.toString(sharedPreferences.getFloat("humidity_a_max",0)));
        puerta_min.setText(Float.toString(sharedPreferences.getFloat("puerta_min",0)));
        puerta_max.setText(Float.toString(sharedPreferences.getFloat("puerta_max",0)));

        not1.setChecked(sharedPreferences.getBoolean(Sensor.SENSORES[0].getTarget() + "_notificacion",true));
        not2.setChecked(sharedPreferences.getBoolean(Sensor.SENSORES[1].getTarget() + "_notificacion",true));
        not3.setChecked(sharedPreferences.getBoolean(Sensor.SENSORES[2].getTarget() + "_notificacion",true));
        not4.setChecked(sharedPreferences.getBoolean(Sensor.SENSORES[3].getTarget() + "_notificacion",true));

    }

    public void guardar(View v){

        EditText url = (EditText)findViewById(R.id.url);

        EditText low_one_min = (EditText)findViewById(R.id.low_one_min);
        EditText low_one_max = (EditText)findViewById(R.id.low_one_max);
        EditText temperature_a_min = (EditText)findViewById(R.id.temperature_a_min);
        EditText temperature_a_max = (EditText)findViewById(R.id.temperature_a_max);
        EditText humidity_a_min = (EditText)findViewById(R.id.humidity_a_min);
        EditText humidity_a_max = (EditText)findViewById(R.id.humidity_a_max);
        EditText puerta_min = (EditText)findViewById(R.id.puerta_min);
        EditText puerta_max = (EditText)findViewById(R.id.puerta_max);

        EditText temperature_low_one = (EditText)findViewById(R.id.temperature_low_one);
        EditText temperature_a = (EditText)findViewById(R.id.temperature_A);
        EditText humidity_a = (EditText)findViewById(R.id.humidity_A);
        EditText puerta = (EditText)findViewById(R.id.puerta);

        CheckBox not1 = (CheckBox)findViewById(R.id.not1);
        CheckBox not2 = (CheckBox)findViewById(R.id.not2);
        CheckBox not3 = (CheckBox)findViewById(R.id.not3);
        CheckBox not4 = (CheckBox)findViewById(R.id.not4);

        SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("url",url.getText().toString().replaceAll("/$",""));

        editor.putFloat("temperature_low_one_min", Float.parseFloat(low_one_min.getText().toString()));
        editor.putFloat("temperature_low_one_max", Float.parseFloat(low_one_max.getText().toString()));
        editor.putFloat("temperature_a_min", Float.parseFloat(temperature_a_min.getText().toString()));
        editor.putFloat("temperature_a_max", Float.parseFloat(temperature_a_max.getText().toString()));
        editor.putFloat("humidity_a_min", Float.parseFloat(humidity_a_min.getText().toString()));
        editor.putFloat("humidity_a_max", Float.parseFloat(humidity_a_max.getText().toString()));
        editor.putFloat("puerta_min", Float.parseFloat(puerta_min.getText().toString()));
        editor.putFloat("puerta_max", Float.parseFloat(puerta_max.getText().toString()));

        editor.putString(Sensor.SENSORES[0].getTarget(),temperature_low_one.getText().toString());
        editor.putString(Sensor.SENSORES[1].getTarget(),temperature_a.getText().toString());
        editor.putString(Sensor.SENSORES[2].getTarget(),humidity_a.getText().toString());
        editor.putString(Sensor.SENSORES[3].getTarget(),puerta.getText().toString());

        editor.putBoolean(Sensor.SENSORES[0].getTarget() + "_notificacion",not1.isChecked());
        editor.putBoolean(Sensor.SENSORES[1].getTarget() + "_notificacion",not2.isChecked());
        editor.putBoolean(Sensor.SENSORES[2].getTarget() + "_notificacion",not3.isChecked());
        editor.putBoolean(Sensor.SENSORES[3].getTarget() + "_notificacion",not4.isChecked());
        editor.commit();

        Sensor.setNombres(this);
        Sensor.setNotificaciones(this);

        finish();
    }
}
