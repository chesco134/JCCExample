package com.example.brenda.jccexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Pais;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brenda on 08/03/2017.
 */
public class AccionesLectura {

    public static Pais[] obtenerPaises(Context context){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Pais", null);
        List<Pais> paises = new ArrayList<>();
        Pais pais;
        while(c.moveToNext()){
            pais = new Pais();
            pais.setIdPais(c.getString(c.getColumnIndex("idPais")));
            pais.setPais(c.getString(c.getColumnIndex("pais")));
            paises.add(pais);
        }
        c.close();
        db.close();
        return paises.toArray(new Pais[]{});
    }

    public static Pais obtenerPais(Context context, String pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Pais where pais = ?", new String[]{pais});
        Pais _pais = null;
        if(c.moveToFirst()){
            _pais = new Pais();
            _pais.setIdPais(c.getString(c.getColumnIndex("idPais")));
            _pais.setPais(pais);
        }
        c.close();
        db.close();
        return _pais;
    }

    public static DatoInteres[] obtenerDatosInteres(Context context){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from DatoInteres", null);
        List<DatoInteres> datosInteres = new ArrayList<>();
        DatoInteres datoInteres;
        while(c.moveToNext()){
            datoInteres = new DatoInteres();
            datoInteres.setIdDatoInteres(c.getString(c.getColumnIndex("idDatoInteres")));
            datoInteres.setDatoInteres(c.getString(c.getColumnIndex("DatoInteres")));
            datoInteres.setPaisIdPais(c.getString(c.getColumnIndex("Pais_idPais")));
            datosInteres.add(datoInteres);
        }
        c.close();
        db.close();
        return datosInteres.toArray(new DatoInteres[]{});
    }

    public static DatoInteres obtenerDatoInteresPais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from DatoInteres where Pais_idPais = ?", new String[]{pais.getIdPais()});
        DatoInteres datoInteres = null;
        if(c.moveToFirst()){
            datoInteres = new DatoInteres();
            datoInteres.setIdDatoInteres(c.getString(c.getColumnIndex("idDatoInteres")));
            datoInteres.setDatoInteres(c.getString(c.getColumnIndex("DatoInteres")));
            datoInteres.setPaisIdPais(pais.getIdPais());
        }
        c.close();
        db.close();
        return datoInteres;
    }
}
