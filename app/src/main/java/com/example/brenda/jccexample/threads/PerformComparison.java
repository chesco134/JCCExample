package com.example.brenda.jccexample.threads;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.brenda.jccexample.database.MyDB;
import com.example.brenda.jccexample.dialogos.ProveedorToast;
import com.example.brenda.jccexample.pojo.Modismo;
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
    private OnFinishActions actions;
    private ArrayList<MyValue> valuesD;
    private ArrayList<MyValue> top5;
    private ArrayList<MyValue> bestWishes;

    public PerformComparison(Context context, String reference){
        this.context = context;
        this.reference = reference;
        this.actions = new OnFinishActions() {
            @Override
            public void activityActions(PerformComparison pc) {}
        };
    }

    public PerformComparison(Context context, String reference, OnFinishActions actions){
        this.context = context;
        this.reference = reference;
        this.actions = actions;
    }

    public ArrayList<MyValue> getValuesD() {
        return valuesD;
    }

    public void setValuesD(ArrayList<MyValue> valuesD) {
        this.valuesD = valuesD;
    }

    public ArrayList<MyValue> getTop5() {
        return top5;
    }

    public void setTop5(ArrayList<MyValue> top5) {
        this.top5 = top5;
    }

    public ArrayList<MyValue> getBestWishes() {
        return bestWishes;
    }

    public void setBestWishes(ArrayList<MyValue> bestWishes) {
        this.bestWishes = bestWishes;
    }

    @Override
    public void run(){
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor c = db.rawQuery("select * from Modismo", null);
        Algorithms algs = new Algorithms();
        algs.setStr1(reference.toUpperCase());
        MyValue[] values = new MyValue[4];
        MyValue[] valuesBestWishes;
        top5 = new ArrayList<>();
        bestWishes = new ArrayList<>();
        valuesD = new ArrayList<>();
        Modismo modismo;
        MyValue value;
        MyValue valueBestWishes;
        Log.d("PerformComparison", "We got: " + c.getCount() + " fields.");
        while(c.moveToNext()){
            algs.setStr2(c.getString(c.getColumnIndex("Expresion")).toUpperCase());
            modismo = new Modismo(c.getInt(c.getColumnIndex("idModismo")), c.getString(c.getColumnIndex("Expresion")), c.getInt(c.getColumnIndex("idPais")));
            values[0] = new MyValue(algs.getStr2(),algs.doEditInformatica());
            values[0].setModismo(modismo);
            values[1] = new MyValue(algs.getStr2(),algs.doHamming());
            values[1].setModismo(modismo);
            values[2] = new MyValue(algs.getStr2(),algs.doBigramInformatica());
            values[2].setModismo(modismo);
            values[3] = new MyValue(algs.getStr2(),algs.doJaroInformatica());
            values[3].setModismo(modismo);
            Log.d("Values", algs.getStr1() + " vs " + algs.getStr2() + "\n\tEdit: " + values[0].getValue()
                + "\n\tHamming: " + values[1].getValue()
                + "\n\tBigram: " + values[2].getValue()
                + "\n\tJaro: " + values[3].getValue());
            if((value=getGreatest(values)).getValue() >= 0.8d ) {
                valuesD.add(value);
            }
            bestWishes.add(getGreatest(values));
        }
        c.close();
        db.close();
        value = getGreatest(values = valuesD.toArray(new MyValue[]{}));
        valueBestWishes = getGreatest(valuesBestWishes = bestWishes.toArray(new MyValue[]{}));
        if(value.getValue() >= 0.8d){
            top5.addAll(Arrays.asList(values).subList(0, (values.length > 4 ? 5 : values.length)));
            Log.e("BackReader", "There were values!");
            for(MyValue mValue : top5)
                Log.d("BackReader", mValue.getExpression() + ": " + mValue.getValue());
        }else {
            Log.e("BackReader", "There were no valuable coincidences. Giving best wishes:");
            for (MyValue mValue : valuesBestWishes)
                Log.d("BackReader", mValue.getExpression() + ": " + mValue.getValue());
        }
        actions.activityActions(this);
        displayNotification();
    }

    private void displayNotification(){
        ((Activity)context).runOnUiThread(new Runnable(){ @Override public void run(){
            ProveedorToast.showToast(context,"Done. Review Logs."); } });
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
        return values != null && values.length > 0 ? values[0] : new MyValue("", 0d);
    }

    public class MyValue{

        private String expression;
        private Double value;
        private Modismo modismo;

        MyValue(String expression, Double value) {
            this.expression = expression;
            this.value = value;
        }

        @Override
        public String toString(){
            return expression + ": " + value;
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

        public Modismo getModismo() {
            return modismo;
        }

        public void setModismo(Modismo modismo) {
            this.modismo = modismo;
        }
    }

    public interface OnFinishActions{
        void activityActions(PerformComparison pc);
    }
}
