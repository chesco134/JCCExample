package com.example.brenda.jccexample;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.fragmentos.DummyDisplayFragment;
import com.example.brenda.jccexample.fragmentos.ListaPaisesFragment;
import com.example.brenda.jccexample.gps.MyLocationProvider;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.PaisParser;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static boolean HAY_ERROR = false;
    private String message;
    private View root;
    private MyLocationProvider mlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.activity_main_main_container);
        mlp = new MyLocationProvider();
    }

    @Override
    public void onResume(){
        super.onResume();
        // startActivity(new Intent(this, MyVoiceActivity.class));
        Log.d("Matsu", "La cantidad de paises es: " + AccionesLectura.obtenerPaises(this).length);
        int permissionCheck = ContextCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(HAY_ERROR){
            final DummyDisplayFragment ddf = new DummyDisplayFragment();
            Bundle args = new Bundle();
            args.putString("message", message);
            ddf.setArguments(args);
            cambioDeFragment(ddf);
            HAY_ERROR = false;
        }
        else
        if(AccionesLectura.obtenerPaises(this).length == 0)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                mlp.setActivity(this);
                mlp.createService();
            }else{
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        makeSnackbar("Necesitamos permiso de " +
                                "ubicación");
                    }
                });
            }
        else
   cambioDeFragmentConBackStack(new ListaPaisesFragment());

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("message", message);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        message = savedInstanceState.getString("message");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_retry) {
            if(!mlp.isConnected()) {
                mlp.setActivity(this);
                mlp.createService();
            }
            else
                mlp.onResume();
        }
        return true;
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

    public void updateLocationData(double latitud, double longitud) {
        Log.d("ULD", "Requesting location data");
        new ContactoConServidor(this, 3/*1*/, ProveedorSolicitudes.solicitudPais(latitud, longitud)/*ProveedorSolicitudes.solicitudSimple()*/, new ContactoConServidor.AccionContactoConServidor() {
            @Override
            public void accionPositiva(String content) {
                Log.d("ULD", "Got: " + content);
                try{
                    JSONObject json = new JSONObject(content);
                    if(json.getBoolean("content")) {
                        AccionesEscritura.escribePais(MainActivity.this, PaisParser.parsePais(json.getJSONObject("Pais")));
                        try {
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                           cambioDeFragmentConBackStack(new ListaPaisesFragment());
                                }
                            });
                        }catch(IllegalStateException ex){
                            HAY_ERROR = false;
                        }
                        mlp.stopLocationUpdates();
                    }else{
                        message = "Unicación desconocida";
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
                   cambioDeFragmentConBackStack(new DummyDisplayFragment());
                        }catch(IllegalStateException ex){
                            HAY_ERROR = true;
                        }
                    }
                });
            }
        }).start();
    }

    public void setLatitudeText(String s) {

    }

    public void setLongitudeText(String s) {

    }

    private void makeSnackbar(String message){
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
                .setAction("Aviso", null).show();
    }
}
