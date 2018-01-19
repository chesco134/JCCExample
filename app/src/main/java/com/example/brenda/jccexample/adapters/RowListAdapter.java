package com.example.brenda.jccexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.example.brenda.jccexample.dialogos.ActualizacionDeModismo;
import com.example.brenda.jccexample.dialogos.RegistroDeModismo;
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
    private RowListListener rll;

    public RowListAdapter(Context context, RowListListener rll){
        this.context = context;
        modismos = new ArrayList<>();
        Collections.addAll(modismos, AccionesLectura.obtenerModismos(context));
        this.rll = rll;
    }

    public RowListAdapter(Context context, List<Modismo> modismos, RowListListener rll){
        this.context = context;
        this.modismos = modismos;
        this.rll = rll;
    }

    public boolean remove(Modismo modismo){
        boolean flag = modismos.remove(modismo);
        notifyDataSetChanged();
        return flag;
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
        final View rootView = convertView != null ? convertView : ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.modismo_row_view, parent, false);
        ((TextView) rootView.findViewById(R.id.modismo_row_view_number)).setText(String.valueOf(position + 1));
        ((TextView) rootView.findViewById(R.id.modismo_row_view_expresion)).setText(modismos.get(position).getExpresion());
        ((TextView) rootView.findViewById(R.id.modismo_row_view_significado)).setText(AccionesLectura.obtenerSignificado(context, modismos.get(position)).getSignificado());
        ((TextView) rootView.findViewById(R.id.modismo_row_view_pais)).setText(AccionesLectura.getIdPaisFromText(context, modismos.get(position).getPais()));
        /*rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });*/
        return rootView;
    }

    public interface RowListListener{
        void clickAction(Fragment fragment);
    }
}
