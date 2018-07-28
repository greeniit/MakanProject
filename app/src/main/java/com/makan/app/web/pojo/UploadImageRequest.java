package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UploadImageRequest {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("img")
    @Expose
    private String img;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
