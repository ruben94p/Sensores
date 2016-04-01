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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraficaActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Sensor sensor;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);
        sensor = (Sensor)getIntent().getSerializableExtra("sensor");

        TextView textView = (TextView)findViewById(R.id.sensor_name);
        textView.setText(sensor.getTarget());

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
        SensorGraphAsyncTask asyncTask = new SensorGraphAsyncTask(this, tiempo, format);
        asyncTask.execute(sensor);
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

    /*
    Probando la grafica
    private void prueba(){

        ArrayList<String> x = new ArrayList<String>();
        ArrayList<Entry> valores = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            x.add(Integer.toString(i+1));
            valores.add(new Entry((float)(Math.random() * 10),(i+1)));
        }

        LineDataSet set1 = new LineDataSet(valores, "Valores");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(x, dataSets);
        lineChart.setData(data);
    }*/

    protected void setValues(Sensor sensor){

            ArrayList<DataPoint> val = sensor.getDatapoints();

            ArrayList<String> x = new ArrayList<>();
            ArrayList<Entry> valores = new ArrayList<>();
            for (int i = 0; i < val.size(); i++) {
                x.add(Integer.toString(i + 1));
                if(val.get(i).isNull){

                }else {
                    valores.add(new Entry(val.get(i).valor, (i + 1)));
                }
            }

            LineDataSet set1 = new LineDataSet(valores, "Valores");

            set1.setColor(Color.BLUE);
            set1.setCircleColor(Color.BLUE);
            set1.setLineWidth(2.5f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setDrawCircleHole(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(x, dataSets);
            lineChart.setData(data);

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
