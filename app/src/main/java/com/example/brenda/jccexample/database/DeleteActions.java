package com.example.brenda.jccexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.pojo.Significado;

/**
 * Created by jcapiz on 11/11/17.
 */

public class DeleteActions {

    public static boolean IcanDeleteTheCountry(Context context, Pais pais){
        boolean deleted;
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        deleted = db.delete("Pais", "idPais = cast(? as integer)", new String[]{String.valueOf(pais.getIdPais())}) > 0;
        db.close();
        return deleted;
    }

    public static boolean deleteDatoInteres(Context context, DatoInteres datoInteres){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        boolean deleted = db.delete("DatoInteres", "idDatoInteres = cast(? as integer)", new String[]{String.valueOf(datoInteres.getIdDatoInteres())}) > 0;
        db.close();
        return deleted;
    }

    public static boolean deleteModismo(Context context, Modismo modismo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        boolean deleted = db.delete("Modismo", "idModismo = cast(? as integer)", new String[]{String.valueOf(modismo.getIdModismo())}) > 0;
        db.close();
        return deleted;
    }

    public static boolean deleteSignificado(Context context, Significado significado){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        boolean deleted = db.delete("Significado", "iidSignificado = cast(? as integer)", new String[]{String.valueOf(significado.getIdSignificado())}) > 0;
        db.close();
        return deleted;
    }

    public static boolean deleteEjemplo(Context context, Ejemplo ejemplo){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        boolean deleted = db.delete("Ejemplo", "idEjemplo = cast(? as integer)", new String[]{String.valueOf(ejemplo.getIdEjemplo())}) > 0;
        db.close();
        return deleted;
    }

    public static boolean deleteModismoRelacion(Context context, ModismoRelacion modismoRelacion){
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        boolean deleted = db.delete("ModismoRelacion", "idModismo_1 = cast(? as integrar) and idModismo_2 = cast(? as integer)", new String[]{String.valueOf(modismoRelacion.getIdModismo1()), String.valueOf(modismoRelacion.getIdModismo2())}) > 0;
        db.close();
        return deleted;
    }
}
