package com.sensores.inmegen.sensores;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class ListGrupoActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grupo);

        setListAdapter(new GrupoAdapter(this,Grupo.GRUPOS));

        final ListGrupoActivity ref = this;
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo.GRUPOS.remove(position);
                LinkedHashSet<String> set = Grupo.crearHashSet();
                SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("grupos",set);

                editor.commit();
                Grupo.setGrupos(ref);
                setListAdapter(new GrupoAdapter(ref, Grupo.GRUPOS));
                return false;
            }
        });
    }

    public void agregarGrupo(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogo_grupo);
        dialog.setTitle("Creando grupo");

        final ListGrupoActivity ref = this;

        final EditText grupo = (EditText)dialog.findViewById(R.id.grupo);
        Button crear = (Button)dialog.findViewById(R.id.crear);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedHashSet<String> set = Grupo.crearHashSet();
                String nuevo = grupo.getText().toString();

                if(set.contains(nuevo)){
                    Toast.makeText(ref, "Este grupo ya existe", Toast.LENGTH_SHORT).show();
                }else {

                    set.add(nuevo);

                    SharedPreferences sharedPreferences = getSharedPreferences("opciones", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("grupos",set);

                    editor.commit();

                    Toast.makeText(ref, "Grupo creado", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                    Grupo.setGrupos(ref);
                    setListAdapter(new GrupoAdapter(ref, Grupo.GRUPOS));
                }
            }
        });

        dialog.show();
    }




}
