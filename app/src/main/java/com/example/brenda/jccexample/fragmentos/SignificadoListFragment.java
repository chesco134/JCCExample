package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.CentralPoint;
import com.example.brenda.jccexample.activities.SimpleListShowActivity;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.ModismoRelacion;
import com.example.brenda.jccexample.pojo.Similar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcapiz on 16/10/17.
 */

public class SignificadoListFragment extends Fragment{

    private Context context;
    private int idModismo;

    @Override
    public void onAttach(Context context){
        super.onAttach(context); this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.significado_fragment_layout, parent, false);
        idModismo = savedInstanceState == null ? getArguments().getInt("idModismo") : savedInstanceState.getInt("idModismo");
        final Modismo modismo;
        modismo = AccionesLectura.obtenerModismo(context, idModismo);
        ((AppCompatActivity)context).getSupportActionBar().setTitle(modismo.getExpresion());
        ((TextView)rootView.findViewById(R.id.significado_fragment_layout_ejemplo_value)).setText(AccionesLectura.obtenerEjemplo(context, modismo).getEjemplo());
        Similar similar = AccionesLectura.obtenerSimilar(context, modismo);
        ((TextView)rootView.findViewById(R.id.significado_fragment_layout_relacionado_value)).setText((null == similar.getSimilar() ? getString(R.string.no_modismos_relacionados) : similar.getSimilar()));
        /*rootView.findViewById(R.id.significado_fragment_layout_ver_relacionados).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                List<String> modismos = new ArrayList<>();
                Similar similar = AccionesLectura.obtenerSimilar(context, modismo);
                modismos.add(similar.getSimilar());
                Log.e("Significado", "Added: " + similar.getSimilar());
                Bundle args = new Bundle();
                args.putStringArray("modismos", modismos.toArray(new String[]{}));
                SimpleListFragment slf = new SimpleListFragment();
                slf.setArguments(args);
                ((SimpleListShowActivity)context).changeFragmentWithBackstack(slf);
            }
        });*/
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("idModismo", idModismo);
    }
}
