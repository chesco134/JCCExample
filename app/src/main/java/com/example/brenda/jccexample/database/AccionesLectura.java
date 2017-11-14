package com.example.brenda.jccexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.pojo.Similar;

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
            pais.setIdPais(c.getInt(c.getColumnIndex("idPais")));
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
        Pais _pais = new Pais();
        _pais.setIdPais(-1);
        if(c.moveToFirst()){
            _pais = new Pais();
            _pais.setIdPais(c.getInt(c.getColumnIndex("idPais")));
            _pais.setPais(pais);
        }
        c.close();
        db.close();
        return _pais;
    }

    public static int getIdPaisFromPais(Context context, String pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select idPais from Pais where Pais = ?", new String[]{pais});
        int idPais = -1;
        if(c.moveToFirst()){
            c.getInt(c.getColumnIndex("idPais"));
        }
        c.close();
        db.close();
        return idPais;
    }

    public static String getIdPaisFromText(Context context, int idPais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select pais from Pais where idPais = cast(? as Integer)", new String[]{String.valueOf(idPais)});
        String pais = null;
        if(c.moveToFirst()){
            pais = c.getString(c.getColumnIndex("pais"));
        }
        c.close();
        db.close();
        return pais;
    }

    public static DatoInteres[] obtenerDatosInteres(Context context){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from DatoInteres", null);
        List<DatoInteres> datosInteres = new ArrayList<>();
        DatoInteres datoInteres;
        while(c.moveToNext()){
            datoInteres = new DatoInteres();
            datoInteres.setIdDatoInteres(c.getInt(c.getColumnIndex("idDatoInteres")));
            datoInteres.setDatoInteres(c.getString(c.getColumnIndex("DatoInteres")));
            datoInteres.setPaisIdPais(c.getInt(c.getColumnIndex("Pais_idPais")));
            datosInteres.add(datoInteres);
        }
        c.close();
        db.close();
        return datosInteres.toArray(new DatoInteres[]{});
    }

    public static DatoInteres[] obtenerDatosInteresPais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from DatoInteres where Pais_idPais = cast(? as integer)", new String[]{String.valueOf(pais.getIdPais())});
        List<DatoInteres> datosInteres = new ArrayList<>();
        DatoInteres datoInteres;
        while(c.moveToNext()){
            datoInteres = new DatoInteres();
            datoInteres.setIdDatoInteres(c.getInt(c.getColumnIndex("idDatoInteres")));
            datoInteres.setDatoInteres(c.getString(c.getColumnIndex("DatoInteres")));
            datoInteres.setPaisIdPais(pais.getIdPais());
            datosInteres.add(datoInteres);
        }
        c.close();
        db.close();
        return datosInteres.toArray(new DatoInteres[]{});
    }

    public static DatoInteres obtenerDatoInteresPais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from DatoInteres where Pais_idPais = ?", new String[]{String.valueOf(pais.getIdPais())});
        DatoInteres datoInteres = new DatoInteres();
        datoInteres.setIdDatoInteres(-1);
        if(c.moveToFirst()){
            datoInteres = new DatoInteres();
            datoInteres.setIdDatoInteres(c.getInt(c.getColumnIndex("idDatoInteres")));
            datoInteres.setDatoInteres(c.getString(c.getColumnIndex("DatoInteres")));
            datoInteres.setPaisIdPais(pais.getIdPais());
        }
        c.close();
        db.close();
        return datoInteres;
    }

    public static Modismo[] obtenerModismoPorPais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Modismo where idPais = ?", new String[] {String.valueOf(pais.getIdPais())});
        List<Modismo> modismos = new ArrayList<>();
        Modismo modismo;
        if(c.moveToFirst()) {
            while (c.moveToNext()){
                modismo = new Modismo(c.getInt(c.getColumnIndex("idmodismo")));
                modismo.setExpresion(c.getString(c.getColumnIndex("expresin")));
                modismo.setPais(pais.getIdPais());
                modismos.add(modismo);
            }
        }
        c.close();
        db.close();
        return modismos.toArray(new Modismo[]{});
    }

    public static Modismo[] obtenerModismos(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Modismo", null);
        List<Modismo> modismos = new ArrayList<>();
        Modismo modismo;
        if(c.moveToFirst()) {
            while (c.moveToNext()){
                modismo = new Modismo(c.getInt(c.getColumnIndex("idModismo")));
                modismo.setExpresion(c.getString(c.getColumnIndex("Expresion")));
                modismo.setPais(c.getInt(c.getColumnIndex("idPais")));
                modismos.add(modismo);
            }
        }else{
            Log.d(AccionesLectura.class.getName(), "No hay modismos.");
        }
        c.close();
        db.close();
        return modismos.toArray(new Modismo[]{});
    }

    public static Modismo obtenerModismo(Context context, int idModismo){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Modismo where idModismo = cast(? as integer)", new String[] {String.valueOf(idModismo)});
        Modismo modismo;
        modismo = c.moveToFirst() ? new Modismo(idModismo, c.getString(c.getColumnIndex("Expresion")), c.getInt(c.getColumnIndex("idPais"))) : new Modismo(-1);
        c.close();
        db.close();
        return modismo;
    }

    public static Modismo obtenerModismo(Context context, String expresion){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Modismo where Expresion = ?", new String[]{expresion});
        Modismo modismo = c.moveToFirst() ? new Modismo(c.getInt(c.getColumnIndex("idModismo")), expresion, c.getInt(c.getColumnIndex("idPais"))) : new Modismo(-1);
        c.close();
        db.close();
        return modismo;
    }

    public static Significado obtenerSignificado(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Significado where idModismo = cast(? as integer)", new String[] {String.valueOf(modismo.getIdModismo())});
        Significado significado = c.moveToFirst() ? new Significado(c.getInt(c.getColumnIndex("idSignificado")), c.getString(c.getColumnIndex("Significado")), modismo.getIdModismo()) : new Significado(-1);
        c.close();
        db.close();
        return significado;
    }

    public static Ejemplo obtenerEjemplo(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Ejemplo where idModismo = cast(? as integer)", new String[]{String.valueOf(modismo.getIdModismo())});
        Ejemplo ejemplo = c.moveToFirst() ? new Ejemplo(c.getInt(c.getColumnIndex("idEjemplo")), c.getString(c.getColumnIndex("Ejemplo")), modismo.getIdModismo()) : new Ejemplo(-1);
        c.close();
        db.close();
        return ejemplo;
    }

    public static Similar obtenerSimilar(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Similar where idModismo = cast(? as integer)", new String[]{String.valueOf(modismo.getIdModismo())});
        Similar similar = c.moveToFirst() ? new Similar(c.getInt(c.getColumnIndex("idSimilar")), c.getString(c.getColumnIndex("Similar")), modismo.getIdModismo()) : new Similar(-1);
        c.close();
        db.close();
        return similar;
    }

    public static ModismoRelacion[] obtenerModismosSimilares(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from ModismoRelacion where idModismo_1 = cast(? as integer)", new String[] {String.valueOf(modismo.getIdModismo())});
        List<ModismoRelacion> modismosRelacionados = new ArrayList<>();
        ModismoRelacion modismoRelacionado;
        while(c.moveToNext()){
            modismoRelacionado = new ModismoRelacion(modismo.getIdModismo(), c.getInt(c.getColumnIndex("idModismo_2")));
            modismosRelacionados.add(modismoRelacionado);
        }
        c.close();
        db.close();
        return modismosRelacionados.toArray(new ModismoRelacion[]{});
    }

    public static int obtenerUltimoIdModismo(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select idModismo from Modismo order by idModismo desc limit 1", null);
        int idModismo = c.moveToFirst() ? c.getInt(c.getColumnIndex("idModismo")) : -1;
        c.close();
        db.close();
        return idModismo;
    }

    public static int obtenerUltimoIdEjemplo(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select idEjemplo from Ejemplo order by idEjemplo desc limit 1", null);
        int idEjemplo = c.moveToFirst() ? c.getInt(c.getColumnIndex("idEjemplo")) : -1;
        c.close();
        db.close();
        return idEjemplo;
    }

    public static int obtenerUltimoIdSignificado(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select idSignificado from Significado order by idSignificado desc limit 1", null);
        int idSignificado = c.moveToFirst() ? c.getInt(c.getColumnIndex("idSignificado")) : -1;
        c.close();
        db.close();
        return idSignificado;
    }

    public static int obtenerUltimoIdSimilar(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select idSimilar from Similar order by idSimilar desc limit 1", null);
        int idSimilar = c.moveToFirst() ? c.getInt(c.getColumnIndex("idSimilar")) : -1;
        c.close();
        db.close();
        return idSimilar;
    }
}
