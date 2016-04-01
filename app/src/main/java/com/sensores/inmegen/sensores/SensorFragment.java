package com.sensores.inmegen.sensores;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {

    private Sensor sensor;

    public SensorFragment() {
        // Required empty public constructor
    }

    public static SensorFragment newInstance(Sensor sensor){
        SensorFragment sensorFragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putSerializable("sensor", sensor);
        sensorFragment.setArguments(args);
        return sensorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            sensor = (Sensor)getArguments().getSerializable("sensor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        TextView sensorText = (TextView)view.findViewById(R.id.sensorTarget);
        TextView sensorValue = (TextView)view.findViewById(R.id.sensorValue);

        if(sensor != null) {
            sensorText.setText(sensor.getTarget());
            sensorValue.setText(Float.toString(sensor.getValorActual()));

            if(sensor.isNull()){
                sensorValue.setText("---");
            }else {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("opciones", Context.MODE_PRIVATE);
                int ver = VerificarSensor.verificar(sharedPreferences, sensor);

                if (ver == 0) {
                    sensorValue.setTextColor(Color.GREEN);
                } else if (ver == -1 || ver == 1) {
                    sensorValue.setTextColor(Color.YELLOW);
                } else if (ver == -2 || ver == 2) {
                    sensorValue.setTextColor(Color.RED);
                }
            }
        }

        return view;
    }

}
