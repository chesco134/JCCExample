package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.adapters.MyRowListAdapter;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.pojo.Pais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Brenda on 08/03/2017.
 */
public class ListaPaisesFragment extends Fragment {

    private GridView gridMenu;
    private Context context;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.grid_fragment, parent, false);
        gridMenu = (GridView) rootView.findViewById(R.id.content_grid_menu);
        List<Pais> nombresPaises = new ArrayList<>();
        Collections.addAll(nombresPaises, AccionesLectura.obtenerPaises(context));
        List<String> purosNombresPaises = new ArrayList<>();
        for(Pais pais : nombresPaises) {
            purosNombresPaises.add(pais.getPais());
            Log.d("Chunchuncha", "Id Pais: " + pais.getIdPais() + ",\t" + pais.getPais());
        }
        MyRowListAdapter adapter = new MyRowListAdapter(context, purosNombresPaises);
        gridMenu.setAdapter(adapter);
        return rootView;
    }
}
