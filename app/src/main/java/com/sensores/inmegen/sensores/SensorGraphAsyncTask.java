package com.sensores.inmegen.sensores;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ruben on 22/03/2016.
 */
public class SensorGraphAsyncTask extends AsyncTask<Sensor,String,Sensor> {

    private ProgressDialog dialogo;
    private GraficaActivity activity;
    private String formato;
    private int tiempo;

    public SensorGraphAsyncTask(GraficaActivity activity, int tiempo, String formato){
        this.activity = activity;
        this.tiempo = tiempo;
        this.formato = formato;
        dialogo = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.setTitle(activity.getString(R.string.cargando));
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected Sensor doInBackground(Sensor... params) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("opciones", Context.MODE_PRIVATE);
        String url = sharedPreferences.getString("url","http://192.168.52.50").replaceAll("/$","");
        for(Sensor sensor : params){
            if(sensor!=null) {
                String json = getJSON(String.format("%s/render?target=%s&format=json&from=-%d%s", url, sensor.getTarget(), tiempo, formato));
                sensor.setDatapoints(new ArrayList<DataPoint>());
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    JSONObject dato = jsonArray.getJSONObject(0);
                    JSONArray datos = dato.getJSONArray("datapoints");
                    for (int i = 0; i < datos.length(); i++) {
                        JSONArray datapoint = datos.getJSONArray(i);
                        if (!datapoint.isNull(0)) {
                            DataPoint d = new DataPoint((float) datapoint.getDouble(0), datapoint.getLong(1));
                            sensor.getDatapoints().add(d);
                        } else {
                            DataPoint d = new DataPoint(0, datapoint.getLong(1), true);
                            sensor.getDatapoints().add(d);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return sensor;
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Sensor sensor) {
        super.onPostExecute(sensor);

        dialogo.dismiss();

        if(sensor != null){
            activity.setValues(sensor);
        }

    }


    private String getJSON(String jsonURL){
        HttpURLConnection connection = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            URL url = new URL(jsonURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.connect();

            int status = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
        }catch (Exception ex)
        {
            Log.e("Error", "Couldn't get JSON. e: " + ex);
        }
        finally
        {
            connection.disconnect();
        }

        return builder.toString();
    }
}
