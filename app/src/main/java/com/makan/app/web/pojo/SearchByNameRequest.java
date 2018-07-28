package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchByNameRequest {

    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
