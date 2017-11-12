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

/**
 * Created by jcapiz on 16/10/17.
 */

public class MyListFragment extends Fragment {

    private Context context;
    private String[] elements;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.simple_list_view, parent, false);
        elements = savedInstanceState == null ? getArguments().getStringArray("elements") : savedInstanceState.getStringArray("elements");
        ((ListView)rootView.findViewById(R.id.simple_list_view_list)).setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, elements));
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putStringArray("elements", elements);
    }
}
