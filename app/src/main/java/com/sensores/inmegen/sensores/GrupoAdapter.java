package com.sensores.inmegen.sensores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ruben on 19/04/2016.
 */
public class GrupoAdapter extends ArrayAdapter<Grupo> {

    public GrupoAdapter(Context context, List<Grupo> list){
        super(context,0,list);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Grupo grupo = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.spinner_layout, parent, false);
        }

        TextView texto = (TextView)convertView.findViewById(R.id.text);
        texto.setText(grupo.getNombre());

        return convertView;
    }
}
