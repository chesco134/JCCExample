package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.adapters.RowListAdapter;
import com.example.brenda.jccexample.pojo.Modismo;

/**
 * Created by jcapiz on 9/10/17.
 */

public class SimpleListFragment extends Fragment {

    private Context context;
    private Modismo[] modismos;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.simple_list_view, parent, false);
        ListView listView = (ListView) rootView.findViewById(R.id.simple_list_view_list);
        Bundle args;
        modismos = (Modismo[]) (savedInstanceState == null ? ((args = getArguments()) != null ? args.getSerializable("modismos") : null) : savedInstanceState.getSerializable("modismos"));
        if(modismos != null)
            listView.setAdapter(new RowListAdapter(context, modismos));
        else
            listView.setAdapter(new RowListAdapter(context));
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putSerializable("modismos", modismos);
    }
}
