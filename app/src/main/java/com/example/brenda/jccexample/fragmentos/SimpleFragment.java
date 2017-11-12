package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brenda.jccexample.R;

/**
 * Created by jcapiz on 8/10/17.
 */

public class SimpleFragment extends Fragment {

    private Context context;
    private TextView textView;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView;
        rootView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_fragment, parent, false);
        textView = (TextView)rootView.findViewById(R.id.hello_world);
        return rootView;
    }

    public TextView getTextView(){
        return textView;
    }
}
