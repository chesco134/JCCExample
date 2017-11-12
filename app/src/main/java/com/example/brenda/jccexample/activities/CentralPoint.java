package com.example.brenda.jccexample.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.dialogos.EntradaTexto;
import com.example.brenda.jccexample.dialogos.ProveedorToast;
import com.example.brenda.jccexample.fragmentos.DummyDisplayFragment;
import com.example.brenda.jccexample.fragmentos.ListaPaisesFragment;
import com.example.brenda.jccexample.fragmentos.SimpleFragment;
import com.example.brenda.jccexample.gps.MyLocationProvider;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.DatoInteresParser;
import com.example.brenda.jccexample.parser.EjemploParser;
import com.example.brenda.jccexample.parser.ModismoParser;
import com.example.brenda.jccexample.parser.PaisParser;
import com.example.brenda.jccexample.parser.ParseModismoRelacion;
import com.example.brenda.jccexample.parser.SignificadoParser;
import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CentralPoint extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int SPEECH_REQUEST_CODE = 0;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 18;

    private int isFirstTime = 0;
    private boolean secondTime;
    private TextView mainTextView;


    private static boolean HAY_ERROR = false;
    private String message;
    private View root;
    private MyLocationProvider mlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_tmp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SimpleFragment sf = new SimpleFragment();
        mainTextView = sf.getTextView();
        cambioDeFragment(sf);

        isFirstTime = savedInstanceState == null ? 0 : savedInstanceState.getInt("is_first_time");
        secondTime = true;
        root = toolbar;
        mlp = new MyLocationProvider();
    }

    @Override
    public void onResume(){
        super.onResume();
        // startActivity(new Intent(this, MyVoiceActivity.class));
        Log.d("Matsu", "La cantidad de paises es: " + AccionesLectura.obtenerPaises(this).length);
        if(HAY_ERROR){
            final DummyDisplayFragment ddf = new DummyDisplayFragment();
            Bundle args = new Bundle();
            args.putString("message", message);
            ddf.setArguments(args);
            cambioDeFragmentConBackStack(ddf);
            HAY_ERROR = false;
        }
        else
            if(AccionesLectura.obtenerPaises(this).length != 0)
       cambioDeFragmentConBackStack(new ListaPaisesFragment());

    }

    @Override
    protected void onPostResume(){
        super.onPostResume();
        if(isFirstTime == 0) {
            isFirstTime ++;
            launchSplash();
        }
        else {
            if("NaN".equals(ProveedorDeRecursos.obtenerRecursoString(this, "host"))){
                setHost();
            }else
                if(isFirstTime == 1) {
                    isFirstTime++;
                    performLocationCheck();
                }
        }
        actualizaNavigationView();
    }

    private void actualizaNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(navigationView.getHeaderCount()-1);
        TextView nombreDeUsuario = (TextView)hView.findViewById(R.id.under_pp);
        nombreDeUsuario.setText(ProveedorDeRecursos.obtenerUsuario(this));
        nombreDeUsuario.setOnClickListener(new ActualizaTextoDesdeEntradaLibre(this, ProveedorDeRecursos.ACTUALIZACION_DE_NOMBRE, ProveedorDeRecursos.obtenerUsuario(this), "Con éste nombre se firmarán los documentos"));
        //((TextView)hView.findViewById(R.id.textView)).setText(ProveedorDeRecursos.obtenerEmail(this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("message", message);
        outState.putInt("is_first_time", isFirstTime);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        message = savedInstanceState.getString("message");
        isFirstTime = savedInstanceState.getInt("is_first_time");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.central_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.action_add_by_voice){
            launchSpeechRecognizer();
            return true;
        }else if (itemId == R.id.action_retry) {
            performLocationCheck();
            return true;
        }else if(itemId == R.id.action_set_host){
            setHost();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void performLocationCheck(){
        int permissionCheck = ContextCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mlp.setActivity(this);
            mlp.createService();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setHost(){
        EntradaTexto et = new EntradaTexto();
        Bundle args = new Bundle();
        args.putString("mensaje", "Host");
        args.putString("content", ProveedorDeRecursos.obtenerRecursoString(this, "host"));
        et.setArguments(args);
        et.setAccionDialogo(new EntradaTexto.AccionDialogo() {
            @Override
            public void accionPositiva(DialogFragment fragment) {
                String input = ((EntradaTexto)fragment).getEntradaDeTexto();
                ProveedorDeRecursos.guardaRecursoString(CentralPoint.this, "host", input);
                ProveedorToast.showToast(CentralPoint.this, "Host cambiado.");
                performLocationCheck();
            }

            @Override
            public void accionNegativa(DialogFragment fragment) {

            }
        });
        et.show(getSupportFragmentManager(), "Entrada Texto");
    }

    public void updateLocationData(double latitud, double longitud) {
        Log.d("ULD", "Requesting location data.\t" + ProveedorDeRecursos.obtenerRecursoString(this, "host"));
        new ContactoConServidor(this, 3/*1*/, ProveedorSolicitudes.solicitudPais(latitud, longitud)/*ProveedorSolicitudes.solicitudSimple()*/, new ContactoConServidor.AccionContactoConServidor() {
            @Override
            public void accionPositiva(String content) {
                Log.d("ULD", "Got: " + content);
                try{
                    JSONObject json = new JSONObject(content);
                    if(json.getBoolean("content")) {
                        Pais pais;
                        ProveedorDeRecursos.guardaRecursoString(CentralPoint.this, ProveedorDeRecursos.PAIS_ACTUAL, PaisParser.parsePais(json.getJSONObject("Pais")).getPais());
                        AccionesEscritura.escribePais(CentralPoint.this, pais = PaisParser.parsePais(json.getJSONObject("Pais")));
                        new ContactoConServidor(CentralPoint.this, 2, ProveedorSolicitudes.solicitudDatoInteres(pais), new ContactoConServidor.AccionContactoConServidor() {
                            @Override
                            public void accionPositiva(String content) {
                                try {
                                    JSONObject jResponse = new JSONObject(content);
                                    JSONArray jDatosInteres = jResponse.getJSONArray("DatosInteres");
                                    DatoInteres datoInteres;
                                    for(int i=0; i<jDatosInteres.length(); i++){
                                        datoInteres = DatoInteresParser.parseDatoInteres(jDatosInteres.getJSONObject(i));
                                        AccionesEscritura.escribeDatoInteres(CentralPoint.this, datoInteres);
                                    }
                                }catch(JSONException ignore){}
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
                        new ContactoConServidor(CentralPoint.this, 4, ProveedorSolicitudes.solicitudModismos(pais), new ContactoConServidor.AccionContactoConServidor() {
                            @Override
                            public void accionPositiva(String content) {
                                Log.d("ULD", "Got: " + content);
                                try{
                                    JSONObject response = new JSONObject(content);
                                    JSONArray jModismos = response.getJSONArray("Modismos");
                                    JSONObject jModismo;
                                    Modismo modismo;
                                    for(int i=0; i<jModismos.length(); i++){
                                        jModismo = jModismos.getJSONObject(i);
                                        modismo = ModismoParser.parseModismo(jModismo);
                                        AccionesEscritura.escribeModismo(CentralPoint.this, modismo);
                                        new ContactoConServidor(CentralPoint.this, 5, ProveedorSolicitudes.solicitudInformacionModismo(modismo), new ContactoConServidor.AccionContactoConServidor() {
                                            @Override
                                            public void accionPositiva(String content) {
                                                Log.d("ULD", "Got: " + content);
                                                try{
                                                    JSONObject respuesta = new JSONObject(content);
                                                    Ejemplo ejemplo;
                                                    Significado significado;
                                                    ejemplo = EjemploParser.parseEjemplo(respuesta.getJSONObject("Ejemplo"));
                                                    AccionesEscritura.escribeEjemplo(CentralPoint.this, ejemplo);
                                                    significado = SignificadoParser.parseSignificado(respuesta.getJSONObject("Significado"));
                                                    AccionesEscritura.escribeSignificado(CentralPoint.this, significado);
                                                    new ContactoConServidor(CentralPoint.this, 6, ProveedorSolicitudes.solicitudModismosRelacionados(AccionesLectura.obtenerModismo(CentralPoint.this, ejemplo.getIdModismo())), new ContactoConServidor.AccionContactoConServidor() {
                                                        @Override
                                                        public void accionPositiva(String content) {
                                                            try {
                                                                JSONObject respuesta = new JSONObject(content);
                                                                ModismoRelacion[] modismosRelacionados;
                                                                modismosRelacionados = ParseModismoRelacion.parseModismoRelacion(respuesta.getJSONObject("Similar"));
                                                                for (ModismoRelacion modismoRelacionado : modismosRelacionados)
                                                                    AccionesEscritura.escribeModismoRelacion(CentralPoint.this, modismoRelacionado);
                                                            }catch(JSONException ignore){}
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
                                                }catch(JSONException ignore){}
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
                                }catch(JSONException ignore){}
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
                        if(mlp.isConnected())
                            mlp.stopLocationUpdates();
                    }else{
                        ProveedorDeRecursos.guardaRecursoString(CentralPoint.this, ProveedorDeRecursos.PAIS_ACTUAL, "");
                        message = "Ubicación desconocida";
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
                if(mlp.isConnected())
                    mlp.stopLocationUpdates();
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

    private void launchSpeechRecognizer() {
        displaySpeechRecognizer();
    }

    private void launchSplash() {
        startActivity(new Intent(this, SplashScreen.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_agregar_modismo) {
            launchAgregarModismo();
        } else if (id == R.id.nav_ver_modismos) {
            launchVerModismos();
//        } else if (id == R.id.nav_accidentes) {
//            launchAccidentes();
//        } else if (id == R.id.nav_configuracion) {
//            launchConfiguracion();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchAgregarModismo(){
        // Add it to Back Stack
    }

    private void launchVerModismos(){
        // Add it to Back Stack
        //cambioDeFragmentConBackStack(new SimpleListFragment());
        startActivity(new Intent(this, SimpleListShowActivity.class));
    }

    private void launchAccidentes(){

    }

    private void launchConfiguracion(){
        startActivity(new Intent(this, Configuraciones.class));
    }


    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            mainTextView.setText(spokenText);
        }
        Log.d("Atún", "Request code: " + requestCode + ", resultCode: " + resultCode + ", data? " + (data != null) + " $$ " + RESULT_OK);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}