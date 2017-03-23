package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.Pais;

import org.json.JSONException;
import org.json.JSONObject;

public class PaisParser {

	public static JSONObject serializePais(Pais pais){
		JSONObject json;
		try{
			json = new JSONObject();
			json.put("idPais", pais.getIdPais());
			json.put("pais", pais.getPais());
		}catch(JSONException e){
			e.printStackTrace();
			json = null;
		}
		return json;
	}
	
	public static Pais parsePais(JSONObject json){
		Pais pais;
		try{
			pais = new Pais();
			pais.setIdPais(json.getString("idPais"));
			pais.setPais(json.getString("pais"));
		}catch(JSONException e){
			e.printStackTrace();
			pais = null;
		}
		return pais;
	}
}
