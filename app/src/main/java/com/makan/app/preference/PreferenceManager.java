package com.makan.app.preference;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    final String APP_PREFERENCE="APP_PREFERENCE";

    public void setValue(Context context,String key,String value){

        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(Context context,String key){

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE);
        return prefs.getString(key,"");
    }
}
