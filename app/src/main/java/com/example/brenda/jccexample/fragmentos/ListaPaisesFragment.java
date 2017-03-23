package com.example.brenda.jccexample.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brenda.jccexample.MainActivity;
import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.DatoInteresParser;
import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Brenda on 08/03/2017.
 */
public class ListaPaisesFragment extends Fragment {

    private Context context;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.edicion_paises, parent, false);
        ListView listaPaises = (ListView) rootView.findViewById(R.id.edicion_paises_lista_paises);
        List<Pais> nombresPaises = new ArrayList<>();
        Collections.addAll(nombresPaises, AccionesLectura.obtenerPaises(context));
        List<String> purosNombresPaises = new ArrayList<>();
        for(Pais pais : nombresPaises)
            purosNombresPaises.add(pais.getPais());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, purosNombresPaises);
        listaPaises.setAdapter(adapter);
        listaPaises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ContactoConServidor(2, ProveedorSolicitudes.solicitudDatoInteres(AccionesLectura.obtenerPais(context, ((TextView)view).getText().toString())),new ContactoConServidor.AccionContactoConServidor() {
                    @Override
                    public void accionPositiva(String content) {
                        try{
                            JSONObject json = new JSONObject(content);
                            if(json.getBoolean("content")) {
                                JSONArray jarr = json.getJSONArray("DatosInteres");
                                List<String> textos = new ArrayList<>();
                                for (int i = 0; i < jarr.length(); i++)
                                    textos.add(DatoInteresParser.parseDatoInteres(jarr.getJSONObject(i)).getDatoInteres());
                                Bundle args = new Bundle();
                                args.putStringArray("datos_interes", textos.toArray(new String[]{}));
                                final ListaDatoInteres fragment = new ListaDatoInteres();
                                fragment.setArguments(args);
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((MainActivity) context).cambioDeFragmentConBackStack(fragment);
                                    }
                                });
                            }else{
                                muestraMensajeDesdeHilo(context.getString(R.string.mensaje_error_conexion));
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void accionNegativa(String content) {
                        muestraMensajeDesdeHilo(context.getString(R.string.mensaje_error_conexion));
                    }
                }).start();
            }
        });
        return rootView;
    }

    private void muestraMensajeDesdeHilo(final String mensaje){
        ((Activity)context).runOnUiThread(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
