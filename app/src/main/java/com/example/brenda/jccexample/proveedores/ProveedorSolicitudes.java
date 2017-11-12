package com.example.brenda.jccexample.proveedores;

import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.Pais;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brenda on 08/03/2017.
 */
public class ProveedorSolicitudes {

    public static JSONObject solicitudDatoInteres(Pais pais){
        JSONObject json = new JSONObject();
        try{
            json.put("idPais", pais.getIdPais());
            json.put("pais", pais.getPais());
        }catch(JSONException ignore){}
        return json;
    }

    public static JSONObject solicitudSimple(){
        return new JSONObject();
    }

    public static JSONObject solicitudPais(double latitud, double longitud){
        JSONObject json = new JSONObject();
        try{
            json.put("latitud", latitud);
            json.put("longitud", longitud);
        }catch(JSONException ignore){}
        return json;
    }

    public static JSONObject solicitudModismos(Pais pais){
        JSONObject json = new JSONObject();
        try{
            json.put("idPais", pais.getIdPais());
        }catch(JSONException ignore){}
        return json;
    }

    public static JSONObject solicitudInformacionModismo(Modismo modismo){
        JSONObject json = new JSONObject();
        try{
            json.put("Expresion", modismo.getExpresion());
            json.put("pais", modismo.getPais());
        }catch(JSONException ignore){}
        return json;
    }
}
