package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.CentralPoint;

/**
 * Created by Brenda on 08/03/2017.
 */
public class ListaDatoInteres extends Fragment {

    private Context context;
    private String[] datosInteres;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.dato_interes_display, parent, false);
        ListView lista = (ListView)rootView.findViewById(R.id.dato_interes_display_lista);
        datosInteres = savedInstanceState == null ? getArguments().getStringArray("datos_interes")
                : savedInstanceState.getStringArray("datos_interes");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, datosInteres);
        lista.setAdapter(adapter);
        ((CentralPoint)context).dismissWaitingActivity();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putStringArray("datos_interes", datosInteres);
    }
}
