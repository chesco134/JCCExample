package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.CentralPoint;
import com.example.brenda.jccexample.networking.ContactoConServidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.brenda.jccexample.activities.CentralPoint.WORKERS;

/**
 * Created by jcapiz on 12/11/17.
 */

public class HilosTrabajando extends android.support.v4.app.Fragment {

    private Context context;
    private Map<String, ContactoConServidor> hilosTrabajando;
    private List<String> statusIds;
    ListView list;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstnaceState){
        View rootView = inflater.inflate(R.layout.fragmento_hilos_trabajando, parent, false);
        fillHilosTrabajando();
        list = (ListView)rootView.findViewById(R.id.fragmento_hilos_trabajando_lista_hilos);
        statusIds = new ArrayList<>();
        statusIds.addAll(hilosTrabajando.keySet());
        list.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, statusIds));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String label = ((TextView)view).getText().toString();
                ContactoConServidor ccs = hilosTrabajando.get(label);
                ccs.closeConnection();
                hilosTrabajando.remove(ccs.getStatusId());
                statusIds.remove(ccs.getStatusId());
                ((CentralPoint) context).removeHiloTrabajando(ccs);
                list.deferNotifyDataSetChanged();
            }
        });
        return rootView;
    }

    public void workerRemoved(ContactoConServidor ccs){
    }

    private synchronized void fillHilosTrabajando(){
        hilosTrabajando = new TreeMap<>();
        for(ContactoConServidor worker : WORKERS)
            hilosTrabajando.put(worker.getStatusId(), worker);
    }
}
