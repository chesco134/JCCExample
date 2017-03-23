package com.example.brenda.jccexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Pais;

/**
 * Created by Brenda on 08/03/2017.
 */
public class AccionesEscritura {

    public static void escribePais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idPais", pais.getIdPais());
        values.put("pais", pais.getPais());
        db.insert("Pais", "---", values);
        db.close();
    }

    public static void escribeDatoInteres(Context context, DatoInteres datoInteres){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idPais", datoInteres.getIdDatoInteres());
        values.put("pais", datoInteres.getDatoInteres());
        values.put("Pais_idPais", datoInteres.getPaisIdPais());
        db.insert("Pais", "---", values);
        db.close();
    }
}
