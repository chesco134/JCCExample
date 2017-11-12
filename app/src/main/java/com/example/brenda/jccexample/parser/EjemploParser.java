package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.proveedores.RegexpProvider;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jcapiz on 8/10/17.
 */

public class EjemploParser {

    public static JSONObject serializeEjemplo(Ejemplo ejemplo){
        JSONObject json;
        try{
            json = new JSONObject();
            json.put("idEjemplo", ejemplo.getIdEjemplo());
            json.put("Ejemplo", ejemplo.getEjemplo());
            json.put("idModismo", ejemplo.getIdModismo());
        }catch(JSONException e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    public static Ejemplo parseEjemplo(JSONObject json){
        Ejemplo ejemplo;
        try{
            ejemplo = new Ejemplo(-1);
            ejemplo.setIdEjemplo(RegexpProvider.getIntSequenceFromString(json.getString("idEjemplo")));
            ejemplo.setEjemplo(json.getString("Ejemplo"));
            ejemplo.setIdModismo(RegexpProvider.getIntSequenceFromString(json.getString("idModismo")));
        }catch(JSONException e){
            e.printStackTrace();
            ejemplo = null;
        }
        return ejemplo;
    }
}
