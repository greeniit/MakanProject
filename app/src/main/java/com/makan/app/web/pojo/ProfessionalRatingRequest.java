package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalRatingRequest {

    @SerializedName("adds_id")
    @Expose
    private String addsId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("language")
    @Expose
    private String language;

    public String getAddsId() {
        return addsId;
    }

    public void setAddsId(String addsId) {
        this.addsId = addsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}