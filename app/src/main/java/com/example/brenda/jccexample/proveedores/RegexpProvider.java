package com.example.brenda.jccexample.proveedores;

import android.util.Log;

/**
 * Created by jcapiz on 8/10/17.
 */

public class RegexpProvider {


    public static Integer getIntSequenceFromString(String value){
        java.util.regex.Pattern NUM_PATTERN = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher numPatternMatcher = NUM_PATTERN.matcher(value);
        try{
            if(numPatternMatcher.find()) {
                String chunk = numPatternMatcher.group();
                Log.d("Monster", ":" + chunk);
                return Integer.parseInt(chunk);
            }else{
                return null;
            }
        }catch(IllegalStateException | NumberFormatException e){
            e.printStackTrace();
            return null;
        }
    }
}
