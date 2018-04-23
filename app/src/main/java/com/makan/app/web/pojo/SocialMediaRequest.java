package com.makan.app.web.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialMediaRequest {

    @SerializedName("username")
    @Expose
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
