package com.sensores.inmegen.sensores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class OpcionesGraficaActivity extends AppCompatActivity {

    private EditText tiempo;
    private RadioGroup format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_grafica);

        tiempo = (EditText)findViewById(R.id.tiempo);

        tiempo.setText("60");
        format = (RadioGroup)findViewById(R.id.format_sel);
    }

    public void aceptar(View v){
        int t = 60;
        try {
            t = Integer.parseInt(tiempo.getText().toString());
        }catch(Exception e){
            setResult(RESULT_CANCELED);
            finish();
        }
        String f = Sensor.IN_SECONDS;
        switch(format.getCheckedRadioButtonId()){
            case R.id.seg:
                f = Sensor.IN_SECONDS;
                break;
            case R.id.min:
                f=Sensor.IN_MINUTES;
                break;
            case R.id.horas:
                f = Sensor.IN_HOURS;
                break;
            case R.id.dias:
                f = Sensor.IN_DAYS;
                break;
            case R.id.semanas:
                f = Sensor.IN_WEEKS;
                break;
            case R.id.meses:
                f = Sensor.IN_MONTHS;
                break;
            case R.id.años:
                f = Sensor.IN_YEARS;
                break;
        }


        Intent i = new Intent();
        i.putExtra("tiempo",t);
        i.putExtra("format", f);

        setResult(RESULT_OK,i);
        finish();
    }
}
