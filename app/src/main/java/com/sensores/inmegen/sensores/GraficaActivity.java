package com.sensores.inmegen.sensores;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraficaActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Sensor sensor;
    private Sensor puerta;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);
        sensor = (Sensor)getIntent().getSerializableExtra("sensor");

        if(sensor.getTarget().equals("system.raspberrypi.temperature_low_one")){
            puerta = new Sensor("system.raspberrypi.puerta");
        }


        TextView textView = (TextView)findViewById(R.id.sensor_name);
        textView.setText(sensor.getNombre());

        lineChart = (LineChart)findViewById(R.id.chart);

        lineChart.setDescription("");

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        YAxis y = lineChart.getAxisLeft();
        y.mAxisRange = 0.5f;
        y.setValueFormatter(new CustomValueFormatter());

        y.setLabelCount(4,true);

        asyncTask(60, Sensor.IN_SECONDS);

    }

    private class CustomValueFormatter implements YAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            DecimalFormat mFormat = new DecimalFormat("###,###,##0.000");
            return mFormat.format(value);
        }
    }

    private void asyncTask(int tiempo, String format){
        if(format == null){
            format = Sensor.IN_SECONDS;
        }
        if(format.isEmpty()){
            format = Sensor.IN_SECONDS;
        }
        if(puerta==null) {
            SensorGraphAsyncTask asyncTask = new SensorGraphAsyncTask(this, tiempo, format);
            asyncTask.execute(sensor);
        }else{
            SensorGraphPuertaAsyncTask asyncTask = new SensorGraphPuertaAsyncTask(this, tiempo, format);
            asyncTask.execute(sensor,puerta);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.definir_intervalo:
                Intent i = new Intent(this, OpcionesGraficaActivity.class);
                startActivityForResult(i,1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_grafica, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                asyncTask(data.getIntExtra("tiempo",60),data.getStringExtra("format"));
            }
        }
    }

    protected void setValues(Sensor sensor){

        ArrayList<DataPoint> val = sensor.getDatapoints();

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> valores = new ArrayList<>();
        for (int i = 0; i < val.size(); i++) {
            x.add(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date(val.get(i).tiempo * 1000)));
            if(val.get(i).isNull){

            }else {
                valores.add(new Entry(val.get(i).valor, (i)));
            }
        }

        LineDataSet set1 = new LineDataSet(valores, "Valores");

        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setDrawCircleHole(false);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(x, dataSets);
        lineChart.setData(data);
        lineChart.notifyDataSetChanged();

    }

    protected void setValuesPuerta(ArrayList<Sensor> sensores){

        Sensor sensor = sensores.get(0);
        Sensor puerta = sensores.get(1);

        ArrayList<DataPoint> val = sensor.getDatapoints();
        ArrayList<DataPoint> valp = puerta.getDatapoints();
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> valores = new ArrayList<>();

        for (int i = 0; i < val.size(); i++) {
            x.add(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date(val.get(i).tiempo * 1000)));

            if(val.get(i).isNull){

            }else {
                valores.add(new Entry(val.get(i).valor, (i)));
                if(valp.get(i).isNull){
                    colors.add(Color.BLUE);
                }else {
                    if (valp.get(i).valor == 0) {
                        colors.add(Color.GREEN);
                    } else {
                        colors.add(Color.RED);
                    }
                }
            }
        }

        LineDataSet set1 = new LineDataSet(valores, "Valores");

        set1.setColor(Color.BLUE);
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setDrawCircleHole(false);
        set1.setCircleColors(colors);

        Legend legend = lineChart.getLegend();
        legend.setCustom(new int[]{Color.GREEN, Color.RED, Color.BLUE},new String[]{"Puerta abierta","Puerta cerrada","Desconocido"});

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(x, dataSets);
        lineChart.setData(data);
        lineChart.notifyDataSetChanged();

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        lineChart.highlightValue(e.getXIndex(),dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        lineChart.highlightValue(null);
    }
}
