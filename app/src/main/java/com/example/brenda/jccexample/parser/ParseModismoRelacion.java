package com.example.brenda.jccexample.parser;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.proveedores.RegexpProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcapiz on 8/10/17.
 */

public class ParseModismoRelacion {

    public static ModismoRelacion[] parseModismoRelacion(Context context, JSONObject json){
        List<ModismoRelacion> modismosRelacionados = new ArrayList<>();
        ModismoRelacion modismoRelacionado;
        try {
            Modismo modismo;
            Pais pais;
            String expresion;
            String similar = json.getString("Similar");
            String modismoIdModismo = json.getString("Modismo_idModismo");
            String[] values = similar.split("\\,");
            java.util.regex.Pattern paisPattern = java.util.regex.Pattern.compile("(?<=\\()[a-zA-Z]+(?=\\))");
            java.util.regex.Pattern modismoPattern = java.util.regex.Pattern.compile("[a-zA-Z\\s]+(?=\\s\\()]");
            java.util.regex.Matcher paisMatcher;
            java.util.regex.Matcher modismoMatcher;
            for(String value : values) {
                    try {
                        paisMatcher = paisPattern.matcher(value);
                        modismoMatcher = modismoPattern.matcher(value);
                        paisMatcher.find();
                        modismoMatcher.find();
                        pais = new Pais();
                        pais.setIdPais(AccionesLectura.getIdPaisFromPais(context,paisMatcher.group()));
                        modismo = AccionesLectura.obtenerModismo(context, expresion = modismoMatcher.group());
                        if (modismo == null) {
                            modismo = new Modismo(-1);
                            modismo.setExpresion(expresion);
                            modismo.setPais(pais.getIdPais());
                            try{ AccionesEscritura.escribeModismo(context, modismo); } catch(SQLiteException e){ Log.d(ParseModismoRelacion.class.getName(), e.getMessage() + "\n\tError de Escritura: " + expresion + ", " + pais.getIdPais()); }
                            modismo = AccionesLectura.obtenerModismo(context, expresion);
                        }
                        modismoRelacionado = new ModismoRelacion(RegexpProvider.getIntSequenceFromString(modismoIdModismo), modismo.getIdModismo());
                        modismosRelacionados.add(modismoRelacionado);
                        Log.d("ParseModismoRelacion", "Added: " + modismo.getExpresion() + ", " + modismo.getPais());
                    } catch (IllegalStateException ingnore) {

                    }
            }
        }catch(JSONException ignore){
        }
        return modismosRelacionados.toArray(new ModismoRelacion[]{});
    }
}
