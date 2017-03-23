package com.example.brenda.jccexample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.database.MyDB;
import com.example.brenda.jccexample.fragmentos.DummyDisplayFragment;
import com.example.brenda.jccexample.fragmentos.ListaPaisesFragment;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.PaisParser;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView content;
    private static boolean HAY_ERROR = false;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Matsu", "La cantidad de paises es: " + AccionesLectura.obtenerPaises(this).length);
        if(HAY_ERROR){
            final DummyDisplayFragment ddf = new DummyDisplayFragment();
            Bundle args = new Bundle();
            args.putString("message", message);
            ddf.setArguments(args);
            cambioDeFragment(ddf);
            HAY_ERROR = false;
        }
        else
        if(AccionesLectura.obtenerPaises(this).length == 0){
            new ContactoConServidor(1, ProveedorSolicitudes.solicitudSimple(), new ContactoConServidor.AccionContactoConServidor() {
                @Override
                public void accionPositiva(String content) {
                    try{
                        JSONObject json = new JSONObject(content);
                        JSONArray jarr = json.getJSONArray("Paises");
                        for(int i=0; i<jarr.length(); i++) {
                            AccionesEscritura.escribePais(MainActivity.this, PaisParser.parsePais(jarr.getJSONObject(i)));
                        }
                        try {
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    cambioDeFragment(new ListaPaisesFragment());
                                }
                            });
                        }catch(IllegalStateException ex){
                            HAY_ERROR = false;
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                        message = content + "\n\n" + e.getMessage();
                        final DummyDisplayFragment ddf = new DummyDisplayFragment();
                        Bundle args = new Bundle();
                        args.putString("message", message);
                        ddf.setArguments(args);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        cambioDeFragment(ddf);
                                    }catch(IllegalStateException ex){
                                        HAY_ERROR = true;
                                    }
                                }
                            });
                    }
                }

                @Override
                public void accionNegativa(String content) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cambioDeFragment(new DummyDisplayFragment());
                                }catch(IllegalStateException ex){
                                    HAY_ERROR = true;
                                }
                            }
                        });
                }
            }).start();
        }else{
            cambioDeFragment(new ListaPaisesFragment());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("message", message);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        message = savedInstanceState.getString("message");
    }

    private void cambioDeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_main_container, fragment)
                .commit();
    }

    public void cambioDeFragmentConBackStack(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_main_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}
