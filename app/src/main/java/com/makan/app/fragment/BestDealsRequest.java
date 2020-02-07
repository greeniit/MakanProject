package com.makan.app.fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestDealsRequest {

    @SerializedName("language")
    @Expose
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
