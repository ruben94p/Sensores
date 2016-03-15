package com.sensores.inmegen.sensores;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ruben on 08/03/2016.
 */
public class SensorExternalAsyncTask extends AsyncTask<Sensor, String, ArrayList<Sensor>> {

    private Context context;

    public SensorExternalAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Sensor> doInBackground(Sensor... params) {
        ArrayList<Sensor> sensores = new ArrayList<>();

        for(Sensor sensor : params){

            //sensor.setDatapoints(new ArrayList<DataPoint>());
            String json = getJSON(String.format("http://192.168.52.50/render?target=%s&format=json&from=-60seconds", sensor.getTarget()));
            try {
                JSONArray jsonArray = new JSONArray(json);
                JSONObject dato = jsonArray.getJSONObject(0);
                JSONArray datos = dato.getJSONArray("datapoints");
                for(int i=datos.length()-1;i>=0;i--){
                    JSONArray datapoint = datos.getJSONArray(i);
                    if(!datapoint.isNull(0)){
                        DataPoint d = new DataPoint((float)datapoint.getDouble(0),datapoint.getLong(1));
                        sensor.getDatapoints().add(d);
                        i=0; //break;
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            sensores.add(sensor);
        }

        return sensores;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Sensor> datos) {
        super.onPostExecute(datos);

        int i = 0;
        //Checar datos
        for(Sensor sensor : datos){
            SharedPreferences sharedPreferences = context.getSharedPreferences("opciones", Context.MODE_PRIVATE);
            int ver = VerificarSensor.verificar(sharedPreferences, sensor);
            if(ver == -1){
                //Debajo del minimo
                crearNotificacion(sensor.getTarget(), "Esta debajo del valor minimo", i);
            }else if(ver == 1){
                //Arriba del maximo
                crearNotificacion(sensor.getTarget(), "Esta arriba del valor maximo", i);
            }
            i++;
        }


    }

    private void crearNotificacion(String title, String text, int id){
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_warning)
                        .setContentTitle(title)
                        .setContentText(text);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
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