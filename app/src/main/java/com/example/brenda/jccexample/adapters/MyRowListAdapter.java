package com.example.brenda.jccexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brenda.jccexample.MainActivity;
import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.CentralPoint;
import com.example.brenda.jccexample.activities.ProveedorDeRecursos;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.fragmentos.ListaDatoInteres;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.DatoInteresParser;
import com.example.brenda.jccexample.pojo.DatoInteres;
import com.example.brenda.jccexample.pojo.Pais;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jcapiz on 8/10/17.
 */

public class MyRowListAdapter extends BaseAdapter {

    private List<String> paises;
    private Context context;

    private static final Map<String, Integer> paisesMap = new TreeMap<>();

    static{
        paisesMap.put("México", R.drawable.mexico);
        paisesMap.put("Chile", R.drawable.chile);
        paisesMap.put("España", R.drawable.espana);
        paisesMap.put("Argentina", R.drawable.argentina);
        paisesMap.put("Desconocido", R.drawable.unknow_country);
    }

    public MyRowListAdapter(Context context, List<String> paises){
        this.context = context;
        this.paises = paises;
    }

    @Override
    public int getCount(){
        return paises.size();
    }

    @Override
    public long getItemId(int position){
        return -1;
    }

    @Override
    public Object getItem(int position){
        return paises.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rootView;
        if( convertView == null){
            rootView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.panel_boton, parent, false);
        }else{
            rootView = convertView;
        }
        if(parent.getWidth() > 0){
            rootView.setLayoutParams(new AbsListView.LayoutParams((parent.getWidth()/3) -2, (parent.getWidth()/3) - 2));
        }
        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView panelText = (TextView) rootView.findViewById(R.id.panel_boton_texto);
        panelText.setText(paises.get(position));
        panelText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Black.ttf"));
        ImageView icon = (ImageView) rootView.findViewById(R.id.panel_boton_icono);
        icon.setImageResource(paisesMap.get(paises.get(position)));
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        rootView.setOnClickListener(new ButtonClickHandler(paises.get(position)));
        return rootView;
    }

    private class ButtonClickHandler implements View.OnClickListener{

        private String pais;

        private ButtonClickHandler(String pais){
            this.pais = pais;
        }

        @Override
        public void onClick(View view){
            ((CentralPoint)context).displayWaitingActivity(context.getString(R.string.cargando_datos_interes));
            Pais p;
            ProveedorDeRecursos.guardaRecursoString(context, ProveedorDeRecursos.PAIS_ACTUAL, pais);
            DatoInteres[] datosInteres = AccionesLectura.obtenerDatosInteresPais(context, p = AccionesLectura.obtenerPais(context, pais));
            Log.d("PaisesGrid", "Had: " + p.getIdPais() + " from: " + p.getPais() + " con " + datosInteres.length + " datos de interés.");
            List<String> listaDatosInteres = new ArrayList<>();
            for(DatoInteres datoInteres : datosInteres)
                listaDatosInteres.add(datoInteres.getDatoInteres());
            Bundle args = new Bundle();
            args.putStringArray("datos_interes", listaDatosInteres.toArray(new String[]{}));
            ListaDatoInteres fragment = new ListaDatoInteres();
            fragment.setArguments(args);
            ((CentralPoint) context).cambioDeFragmentConBackStack(fragment);
        }
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
