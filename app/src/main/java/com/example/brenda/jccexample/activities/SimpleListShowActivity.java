package com.example.brenda.jccexample.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.fragmentos.SignificadoListFragment;
import com.example.brenda.jccexample.fragmentos.SimpleListFragment;

/**
 * Created by jcapiz on 11/10/17.
 */

public class SimpleListShowActivity extends AppCompatActivity {

    private FrameLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        Bundle args = getIntent().getExtras();
        if(args != null){
            SignificadoListFragment slf = new SignificadoListFragment();
            Bundle args01 = new Bundle();
            args01.putInt("idModismo", args.getInt("idModismo"));
            slf.setArguments(args01);
            Log.e("SLSA", "We got a Modismo to show from the SearchView (" + args01.getInt("idModismo"));
            changeFragment(slf);
        }
        else
        if(savedInstanceState == null)
        changeFragment(new SimpleListFragment());
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_main_container, fragment)
                .commit();
    }

    public void changeFragmentWithBackstack(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_main_container, fragment)
                .addToBackStack("Info")
                .commit();
    }
}
