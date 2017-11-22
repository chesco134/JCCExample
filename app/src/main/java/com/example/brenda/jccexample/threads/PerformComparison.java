package com.example.brenda.jccexample.threads;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.brenda.jccexample.database.MyDB;
import com.example.brenda.jccexample.proveedores.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Juan Capiz on 22/11/2017.
 */

public class PerformComparison extends Thread {

    private String reference;
    private Context context;

    public PerformComparison(Context context, String reference){
        this.context = context;
        this.reference = reference;
    }

    @Override
    public void run(){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select Expresion from Modismo", null);
        Algorithms algs = new Algorithms();
        algs.setStr1(reference);
        MyValue[] values = new MyValue[4];
        List<MyValue> top5 = new ArrayList<>();
        List<MyValue> bestWishes = new ArrayList<>();
        List<MyValue> valuesD = new ArrayList<>();
        MyValue value;
        while(c.moveToNext()){
            algs.setStr2(c.getString(c.getColumnIndex("Modismo")));
            values[0] = new MyValue(algs.getStr2(),algs.doEdit());
            values[1] = new MyValue(algs.getStr2(),algs.doHamming());
            values[2] = new MyValue(algs.getStr2(),algs.doBigramInformatica());
            values[3] = new MyValue(algs.getStr2(),algs.doJaroInformatica());
            if((value=getGreatest(values)).getValue() >= 0.8d ) {
                valuesD.add(value);
            }
        }
        c.close();
        db.close();
        value = getGreatest(values = valuesD.toArray(new MyValue[]{}));
        if(value.getValue() >= 0.8d){
            top5.addAll(Arrays.asList(values).subList(0, (values.length > 4 ? 5 : values.length)));
            Log.e("BackReader", "There were values!");
            for(MyValue mValue : top5)
                Log.d("BackReader", mValue.getExpression());
        }else{
            bestWishes.addAll(Arrays.asList(values).subList(0, (values.length > 4 ? 5 : values.length)));
            Log.e("BackReader", "There were no valuable coincidences. Giving best wishes:");
            for(MyValue mValue : bestWishes)
                Log.d("BackReader", mValue.getExpression());
        }
    }

    private MyValue getGreatest(MyValue[] values){
        MyValue value;
        for(int i=0; i<values.length; i++)
            for(int j = i+1; j<values.length; j++){
                if(values[i].getValue() < values[j].getValue()){
                    value = values[i];
                    values[i] = values[j];
                    values[j] = value;
                }
            }
        return values[0];
    }

    private class MyValue{

        private String expression;
        private Double value;

        MyValue(String expression, Double value) {
            this.expression = expression;
            this.value = value;
        }

        Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }
}
