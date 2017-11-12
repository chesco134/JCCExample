package com.example.brenda.jccexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Significado;

/**
 * Created by jcapiz on 12/11/17.
 */

public class AccionesActualizacion {

    public static boolean actualizaModismo(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idModismo", modismo.getIdModismo());
        values.put("Expresion", modismo.getExpresion());
        values.put("idPais", modismo.getPais());
        boolean wasUpdated = db.update("Modismo", values, "idModismo = cast(? as integer)", new String[]{String.valueOf(modismo.getIdModismo())}) > 0;
        db.close();
        return wasUpdated;
    }

    public static boolean actualizaEjemplo(Context context, Ejemplo ejemplo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEjemplo", ejemplo.getIdEjemplo());
        values.put("Ejemplo", ejemplo.getEjemplo());
        values.put("idModismo", ejemplo.getIdModismo());
        boolean wasUpdated = db.update("Ejemplo", values, "idEjemplo = cast(? as integer)", new String[]{String.valueOf(ejemplo.getIdEjemplo())}) > 0;
        db.close();
        return wasUpdated;
    }

    public static boolean actualizaSignificado(Context context, Significado significado){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idSignificado", significado.getIdSignificado());
        values.put("Significado", significado.getSignificado());
        values.put("idModismo", significado.getIdModismo());
        boolean wasUpdated = db.update("Significado", values, "idSignificado = cast(? as integer)", new String[]{String.valueOf(significado.getIdSignificado())}) > 0;
        db.close();
        return wasUpdated;
    }

    public static boolean actualizaModismoRelacion(Context context, ModismoRelacion modismoRelacion){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idModismo_1", modismoRelacion.getIdModismo1());
        values.put("idModismo_2", modismoRelacion.getIdModismo2());
        boolean wasUpdated = db.update("Modismo_Relacion", values, "idModismo_1 = cast(? as integer)", new String[]{String.valueOf(modismoRelacion.getIdModismo1())}) > 0;
        db.close();
        return wasUpdated;
    }
}
