package com.example.brenda.jccexample.activities;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.example.brenda.jccexample.R;


public class Configuraciones extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuraciones);
        ((TextView)findViewById(R.id.configuraciones_texto)).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));

    }
}
