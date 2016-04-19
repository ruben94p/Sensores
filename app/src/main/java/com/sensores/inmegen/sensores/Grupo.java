package com.sensores.inmegen.sensores;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Ruben on 15/04/2016.
 */
public class Grupo implements Comparable<Grupo> {

    public static final ArrayList<Grupo> GRUPOS = new ArrayList<>();

    public static void setGrupos(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("opciones", Context.MODE_PRIVATE);
        Set<String> set = new LinkedHashSet<>();
        set.add("Grupo 1");
        set = sharedPreferences.getStringSet("grupos",set);
        if(set.isEmpty()){
            set.add("Grupo 1");
        }
        GRUPOS.clear();
        int id = 1;
        for(String s : set){
            GRUPOS.add(new Grupo(id,s));
            id++;
        }

        Collections.sort(GRUPOS);

        //GRUPOS.add(new Grupo(1,"Refrigerador 1"));
        //GRUPOS.add(new Grupo(2,"Nombre 2"));
    }

    public static LinkedHashSet<String> crearHashSet(){
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for(Grupo grupo : GRUPOS){
            set.add(grupo.getNombre());
        }
        return set;
    }

    public static Grupo getGrupoByNombre(String nombre){
        for(Grupo grupo : GRUPOS){
            if(grupo.nombre.equals(nombre)){
                return grupo;
            }
        }
        return GRUPOS.get(0);
    }


    private int id;
    private String nombre;

    public Grupo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Grupo){
            Grupo g = (Grupo)o;
            return nombre.equals(g.nombre);
        }
        return super.equals(o);
    }

    @Override
    public int compareTo(Grupo another) {
        return nombre.compareTo(another.nombre);
    }
}
