package com.example.brenda.jccexample.activities;

import android.app.Activity;
import android.view.View;

import com.example.brenda.jccexample.R;

/**
 * Created by jcapiz on 18/03/16.
 */
public class EfectoDeEnfoque implements View.OnFocusChangeListener {

    private Activity activity;
    private View referencedView;

    public EfectoDeEnfoque(Activity activity, View referencedView) {
        this.activity = activity;
        this.referencedView = referencedView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
            referencedView
                    .setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
        else
            referencedView
                    .setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
    }
}