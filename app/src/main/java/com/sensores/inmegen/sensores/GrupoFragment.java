package com.sensores.inmegen.sensores;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GrupoFragment extends Fragment {

    private ArrayList<Sensor> sensores;
    private String grupo;

    public GrupoFragment() {
        // Required empty public constructor
    }

    public static GrupoFragment newInstance(ArrayList<Sensor> sensores, String grupo){
        GrupoFragment grupoFragment = new GrupoFragment();
        Bundle args = new Bundle();
        args.putSerializable("sensores", sensores);
        args.putString("grupo", grupo);
        grupoFragment.setArguments(args);
        return grupoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            sensores = (ArrayList<Sensor>)getArguments().getSerializable("sensor");
            grupo = getArguments().getString("grupo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grupo, container, false);

        TextView g = (TextView)view.findViewById(R.id.nombreGrupo);
        g.setText(grupo);

        return view;
    }

}
