package com.makan.app.app;


import android.util.Log;

public class AppLog {

    private final static String TAG="Makan";

    public static void showErrorMessage(String message){

        Log.e(TAG,message);
    }

    public static void showInfoMessage(String message){

        Log.i(TAG,message);
    }
}
