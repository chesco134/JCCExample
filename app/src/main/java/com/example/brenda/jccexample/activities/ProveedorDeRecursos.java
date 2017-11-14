package com.example.brenda.jccexample.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.example.brenda.jccexample.R;


/**
 * Created by jcapiz on 18/03/16.
 */
public class ProveedorDeRecursos {

    public static final int ACTUALIZACION_DE_NOMBRE = 17;
    public static final String PAIS_ACTUAL = "pais_actual";

    public static String obtenerEmail(Context context){
        return context.getSharedPreferences(CentralPoint.class.getName(), Context.MODE_PRIVATE)
                .getString("email", "NaN");
    }

    public static int obtenerColorDeError(Context context){
        return context.getResources().getColor(R.color.error);
    }

    public static String obtenerUsuario(Context context){
        return context.getSharedPreferences(CentralPoint.class.getName(), Context.MODE_PRIVATE)
                .getString("usuario", "NaN");
    }

    public static void guardaRecursoInt(Context context, String nombre, int recurso){
        SharedPreferences.Editor editor = context.getSharedPreferences(Configuraciones.class.getName(), Context.MODE_PRIVATE)
                .edit();
        editor.putInt(nombre, recurso);
        editor.apply();
    }

    public static void guardaRecursoString(Context context, String nombre, String valor){
        SharedPreferences.Editor editor = context.getSharedPreferences(Configuraciones.class.getName(), Context.MODE_PRIVATE)
                .edit();
        editor.putString(nombre, valor);
        editor.apply();
    }

    public static String obtenerRecursoString(Context context, String nombre){
        return context.getSharedPreferences(Configuraciones.class.getName(), Context.MODE_PRIVATE).getString(nombre, "NaN");
    }

    public static int obtenerRecursoInt(Context context, String nombre) {
        return context.getSharedPreferences(Configuraciones.class.getName(), Context.MODE_PRIVATE).getInt(nombre, -1);
    }
}
