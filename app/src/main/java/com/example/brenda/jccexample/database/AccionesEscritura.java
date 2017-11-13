package com.example.brenda.jccexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.pojo.Similar;

/**
 * Created by Brenda on 08/03/2017.
 */
public class AccionesEscritura {

    public static void escribePais(Context context, Pais pais){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idPais", pais.getIdPais());
        values.put("pais", pais.getPais());
        Log.d("AccionesEscritura", "Escribiendo Pais: " + pais.getPais());
        try{ db.insert("Pais", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }

    public static void escribeDatoInteres(Context context, DatoInteres datoInteres){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idDatoInteres", datoInteres.getIdDatoInteres());
        values.put("DatoInteres", datoInteres.getDatoInteres());
        values.put("Pais_idPais", datoInteres.getPaisIdPais());
        try{ db.insert("DatoInteres", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }

    public static void escribeModismo(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idModismo", modismo.getIdModismo());
        values.put("Expresion", modismo.getExpresion());
        values.put("idPais", modismo.getPais());
        try{ db.insert("Modismo", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }

    public static void escribeSignificado(Context context, Significado significado){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idSignificado", significado.getIdSignificado());
        values.put("Significado", significado.getSignificado());
        values.put("idModismo", significado.getIdModismo());
        try{ db.insert("Significado", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }

    public static void escribeEjemplo(Context context, Ejemplo ejemplo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEjemplo", ejemplo.getIdEjemplo());
        values.put("Ejemplo", ejemplo.getEjemplo());
        values.put("idModismo", ejemplo.getIdModismo());
        try{ db.insert("Ejemplo", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }

    public static void escribeSimilar(Context context, Similar similar){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idSimilar", similar.getIdSimilar());
        values.put("Similar", similar.getSimilar());
        values.put("idModismo", similar.getIdModismo());
        try{ db.insert("Similar", "---", values); Log.e("Barra", "Inserted: " + similar.getSimilar()); }catch(SQLiteConstraintException ignore){ignore.printStackTrace(); }
        db.close();
    }

    public static void escribeModismoRelacion(Context context, ModismoRelacion modismoRelacionado){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idModismo_1", modismoRelacionado.getIdModismo1());
        values.put("idModismo_2", modismoRelacionado.getIdModismo2());
        try{ db.insert("ModismoRelacion", "---", values); } catch(SQLiteConstraintException ignore){ }
        db.close();
    }
}
