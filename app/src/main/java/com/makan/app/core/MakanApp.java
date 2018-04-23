package com.makan.app.core;

import android.app.Application;

import com.google.gson.Gson;
import com.makan.app.app.AppState;
import com.makan.app.model.User;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;


public class MakanApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        String userData=new PreferenceManager().getValue(this, PrefKey.USER_DATA);

        if(userData!=null&&userData.length()>0){

            Gson gson=new Gson();
            User user=gson.fromJson(userData, User.class);

            AppState.getInstance().setUserId(user.getId());

            AppState.getInstance().setLoginStatus(true);
        }else{
            AppState.getInstance().setLoginStatus(false);
        }

    }
}
