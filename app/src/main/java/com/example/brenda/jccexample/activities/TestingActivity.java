package com.example.brenda.jccexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.adapters.RowListAdapter;
import com.example.brenda.jccexample.database.MyDB;
import com.example.brenda.jccexample.dialogos.ProveedorToast;
import com.example.brenda.jccexample.fragmentos.SignificadoListFragment;
import com.example.brenda.jccexample.fragmentos.SimpleListFragment;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.threads.PerformComparison;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Capiz on 02/01/2018.
 */

public class TestingActivity extends AppCompatActivity {

    private EditText modismoInput;
    private PerformComparison comparison;
    private ArrayAdapter<PerformComparison.MyValue> adapter;
    private RowListAdapter rla;
    private List<PerformComparison.MyValue> mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_algorythms_layout);
        modismoInput = (EditText) findViewById(R.id.testing_algorythms_layout_modismo);
        String voiceValue;
        Button submit = (Button) findViewById(R.id.testing_algorythms_layout_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comparison = new PerformComparison(TestingActivity.this, modismoInput.getText().toString(), new PerformComparison.OnFinishActions() {
                    @Override
                    public void activityActions(PerformComparison pc) {
                        if(mValues != null)
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for(PerformComparison.MyValue mValue : mValues)
                                            rla.remove(mValue.getModismo());
                                    }
                                });
                        final ArrayList<PerformComparison.MyValue> top5 = pc.getTop5();
                        ArrayList<PerformComparison.MyValue> bestWishes = pc.getBestWishes();
                        final ListView lv = (ListView)findViewById(R.id.testing_algorythms_layout_sample_list);
                        if(top5 != null && top5.size() >= 1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Modismo> modismos = new ArrayList<>();
                                    for(PerformComparison.MyValue mValue : mValues = top5)
                                        modismos.add(mValue.getModismo());
                                    rla = new RowListAdapter(TestingActivity.this, modismos, new RowListAdapter.RowListListener() {
                                        @Override
                                        public void clickAction(Fragment fragment) {
                                            Intent i = new Intent(TestingActivity.this, SimpleListShowActivity.class);
                                            i.putExtra("idModismo", (fragment).getArguments().getInt("idModismo"));
                                            startActivity(i);
//                                            setResult(RESULT_OK,i);
//                                            finish();
                                        }
                                    });
//                                    adapter = new ArrayAdapter<>(TestingActivity.this, android.R.layout.simple_list_item_1, mValues = top5);
//                                    lv.setAdapter(adapter);
                                    lv.setAdapter(rla);
                                }
                            });
                        }else if(top5 != null && top5.size() < 5 && bestWishes != null){
                            final List<PerformComparison.MyValue> values = new ArrayList<>();
                            values.addAll(top5);
                            values.addAll(bestWishes);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // lv.setAdapter(new ArrayAdapter<>(TestingActivity.this, android.R.layout.simple_list_item_1, values));
                                    ProveedorToast.showToast(TestingActivity.this, "No hay modismos parecidos");

                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ProveedorToast.showToast(TestingActivity.this, "No hay modismos parecidos");
                                }
                            });
                        }
                    }
                });
                comparison.start();
            }
        });
        if((voiceValue = getIntent().getStringExtra("voice_value")) != null){
            ((EditText)findViewById(R.id.testing_algorythms_layout_modismo)).setText(voiceValue);
            submit.performClick();
        }
        if(savedInstanceState == null){
//            new MyDB(this).onUpgrade(new MyDB(this).getWritableDatabase(), 1, 2);
//            ProveedorToast.showToast(this, "Base de datos actualizada.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("modismo", modismoInput.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        modismoInput.setText(savedInstanceState.getString("modismo").toString());
    }
}
