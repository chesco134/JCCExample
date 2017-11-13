package com.example.brenda.jccexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.CentralPoint;
import com.example.brenda.jccexample.activities.SimpleListShowActivity;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.fragmentos.SignificadoListFragment;
import com.example.brenda.jccexample.fragmentos.SimpleListFragment;
import com.example.brenda.jccexample.pojo.Modismo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jcapiz on 9/10/17.
 */

public class RowListAdapter extends BaseAdapter {

    private Context context;
    private List<Modismo> modismos;

    public RowListAdapter(Context context){
        this.context = context;
        modismos = new ArrayList<>();
        Collections.addAll(modismos, AccionesLectura.obtenerModismos(context));
    }

    @Override
    public int getCount(){
        return modismos.size();
    }

    @Override
    public long getItemId(int position){
        return -1;
    }

    @Override
    public Object getItem(int position){
        return modismos.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View rootView = convertView != null ? convertView : ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.modismo_row_view, parent, false);
        ((TextView)rootView.findViewById(R.id.modismo_row_view_number)).setText(String.valueOf(position+1));
        ((TextView)rootView.findViewById(R.id.modismo_row_view_expresion)).setText(modismos.get(position).getExpresion());
        ((TextView)rootView.findViewById(R.id.modismo_row_view_significado)).setText(AccionesLectura.obtenerSignificado(context, modismos.get(position)).getSignificado());
        ((TextView)rootView.findViewById(R.id.modismo_row_view_pais)).setText(AccionesLectura.getIdPaisFromText(context,modismos.get(position).getPais()));
        rootView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignificadoListFragment slf = new SignificadoListFragment();
                Bundle args = new Bundle();
                args.putInt("idModismo", modismos.get(position).getIdModismo());
                slf.setArguments(args);
                ((SimpleListShowActivity)context).changeFragmentWithBackstack(slf);
            }
        });
        return rootView;
    }
}
