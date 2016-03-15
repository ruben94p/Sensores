package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ruben on 08/03/2016.
 */
public class SensorArrayAdapter extends ArrayAdapter<Sensor> {

    public SensorArrayAdapter(Context context, List<Sensor> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sensor sensor = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.sensor_layout, parent, false);
        }

        TextView target = (TextView) convertView.findViewById(R.id.target);
        TextView valor = (TextView) convertView.findViewById(R.id.valor);
        target.setText(sensor.getTarget());
        valor.setText(Float.toString(sensor.getValorActual()));


        return convertView;
    }
}
