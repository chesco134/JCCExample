package com.example.brenda.jccexample.networking;

import android.content.Context;
import android.util.Log;

import com.example.brenda.jccexample.activities.ProveedorDeRecursos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Brenda on 25/02/2017.
 */
public class ContactoConServidor extends Thread {

    private Context context;
    private JSONObject json;
    private AccionContactoConServidor accion;
    private String status;
    private int action;

    public ContactoConServidor(Context context, int action, JSONObject json, AccionContactoConServidor accion){
        this.context = context;
        this.action = action;
        this.json = json;
        this.accion = accion;
    }

    @Override
    public void run(){
        try{
            // 68:8080/BMLSampleUnit/Hurricane
            // 73:8080/ServidorBrenda/MainEntrance
            String customURL;
            String host;
            java.net.HttpURLConnection con = // 10.0.2.2
                    (java.net.HttpURLConnection) new java.net.URL(customURL = "http://"+ ("NaN".equals(host = ProveedorDeRecursos.obtenerRecursoString(context, "host")) ? "10.10.90.155" : host)+":8080/BMLSampleUnit/Hurricane").openConnection();
            Log.d("ContactoConServidor", "Connecting to: " + customURL);
            con.setDoOutput(true);
            java.io.DataOutputStream salida = new java.io.DataOutputStream(con.getOutputStream());
            json.put("action", action);
            salida.write(json.toString().getBytes());
            salida.flush();
            java.io.DataInputStream entrada = new java.io.DataInputStream(con.getInputStream());
            byte[] chunk = new byte[256];
            final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            int length;
            while((length = entrada.read(chunk)) != -1)
                baos.write(chunk,0,length);
            status = baos.toString();
            baos.close();
            con.disconnect();
            accion.accionPositiva(status);
        }catch(JSONException | IOException e){
            e.printStackTrace();
            status = e.getMessage();
            accion.accionNegativa(status);
        }
        Log.d("ContactoConServidor", status);
    }

    public interface AccionContactoConServidor{
        void accionPositiva(String content);
        void accionNegativa(String content);
    }
}
