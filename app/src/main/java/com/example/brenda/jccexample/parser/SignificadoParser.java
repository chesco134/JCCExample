package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.proveedores.RegexpProvider;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jcapiz on 8/10/17.
 */

public class SignificadoParser {

    public static JSONObject serializeSignificado(Significado significado){
        JSONObject json;
        try{
            json = new JSONObject();
            json.put("idSignificado", significado.getIdSignificado());
            json.put("Significado", significado.getSignificado());
            json.put("idModismo", significado.getIdModismo());
        }catch(JSONException e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    public static Significado parseSignificado(JSONObject json){
        Significado significado;
        try{
            significado = new Significado(-1);
            significado.setIdSignificado(RegexpProvider.getIntSequenceFromString(json.getString("idSignificado")));
            significado.setSignificado(json.getString("Significado"));
            significado.setIdModismo(RegexpProvider.getIntSequenceFromString(json.getString("idModismo")));
        }catch(JSONException e){
            e.printStackTrace();
            significado = null;
        }
        return significado;
    }
}
