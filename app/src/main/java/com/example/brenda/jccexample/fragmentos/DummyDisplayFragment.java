package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brenda.jccexample.R;


public class DummyDisplayFragment extends Fragment{

    private Context context;
    private String message;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.dummy_display, parent, false);
        try {
            message = savedInstanceState != null ? savedInstanceState.getString("message") : getArguments().getString("message");
            if (message != null)
                ((TextView) rootView.findViewById(R.id.dummy_display_main_lable)).setText(message);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("message", message);
    }
}
