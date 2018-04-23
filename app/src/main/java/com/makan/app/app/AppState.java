package com.makan.app.app;

public class AppState {

    private static AppState instance=null;
    private boolean loginStatus=false;
    private String userId;

    private AppState(){

    }

    public static AppState getInstance(){

        if(instance==null){
            instance=new AppState();
        }

        return instance;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
