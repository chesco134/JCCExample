package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.proveedores.RegexpProvider;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jcapiz on 8/10/17.
 */

public class ModismoParser {

    public static JSONObject serializeModismo(Modismo modismo){
        JSONObject json;
        try{
            json = new JSONObject();
            json.put("idModismo", modismo.getIdModismo());
            json.put("Expresion", modismo.getExpresion());
        }catch(JSONException e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    public static Modismo parseModismo(JSONObject json){
        Modismo modismo;
        try{
            modismo = new Modismo(-1);
            modismo.setIdModismo(RegexpProvider.getIntSequenceFromString(json.getString("idModismo")));
            modismo.setExpresion(json.getString("Expresion"));
            modismo.setPais(json.getInt("idPais"));
        }catch(JSONException e){
            e.printStackTrace();
            modismo = null;
        }
        return modismo;
    }
}
