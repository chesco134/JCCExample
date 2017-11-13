package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.Similar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jcapiz on 13/11/17.
 */

public class SimilarParser {

    public static JSONObject serializeSimilar(Similar similar){
        JSONObject json;
        try{
            json = new JSONObject();
            json.put("idSignificado", similar.getIdSimilar());
            json.put("Significado", similar.getSimilar());
            json.put("idModismo", similar.getIdModismo());
        }catch(JSONException e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    public static Similar parseSimilar(JSONObject json){
        Similar similar;
        try{
            similar = new Similar(-1);
            similar.setIdSimilar(json.getInt("idSimilar"));
            similar.setSimilar(json.getString("Similar"));
            similar.setIdModismo(json.getInt("idModismo"));
        }catch(JSONException e){
            e.printStackTrace();
            similar = null;
        }
        return similar;
    }
}
