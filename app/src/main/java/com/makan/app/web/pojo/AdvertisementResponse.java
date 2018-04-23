package com.makan.app.web.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdvertisementResponse {

    @SerializedName("adds")
    @Expose
    private List<Advertisement> adds = null;
    @SerializedName("res")
    @Expose
    private Integer res;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("IsSuccess")
    @Expose
    private Integer isSuccess;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;

    public List<Advertisement> getAdds() {
        return adds;
    }

    public void setAdds(List<Advertisement> adds) {
        this.adds = adds;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public class Advertisement {

        @SerializedName("adds_id")
        @Expose
        private String addsId;
        @SerializedName("adds_address")
        @Expose
        private String addsAddress;
        @SerializedName("adds_photo")
        @Expose
        private String addsPhoto;
        @SerializedName("adds_website")
        @Expose
        private String addsWebsite;
        @SerializedName("adds_description")
        @Expose
        private String addsDescription;

        public String getAddsId() {
            return addsId;
        }

        public void setAddsId(String addsId) {
            this.addsId = addsId;
        }

        public String getAddsAddress() {
            return addsAddress;
        }

        public void setAddsAddress(String addsAddress) {
            this.addsAddress = addsAddress;
        }

        public String getAddsPhoto() {
            return addsPhoto;
        }

        public void setAddsPhoto(String addsPhoto) {
            this.addsPhoto = addsPhoto;
        }

        public String getAddsWebsite() {
            return addsWebsite;
        }

        public void setAddsWebsite(String addsWebsite) {
            this.addsWebsite = addsWebsite;
        }

        public String getAddsDescription() {
            return addsDescription;
        }

        public void setAddsDescription(String addsDescription) {
            this.addsDescription = addsDescription;
        }

    }
}
