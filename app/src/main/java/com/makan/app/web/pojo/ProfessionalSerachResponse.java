package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionalSerachResponse {

    @SerializedName("professional")
    @Expose
    private List<Professional> professional = null;
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

    public List<Professional> getProfessional() {
        return professional;
    }

    public void setProfessional(List<Professional> professional) {
        this.professional = professional;
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

    public class Professional {

        @SerializedName("adds_id")
        @Expose
        private String addId;
        @SerializedName("adds_lat")
        @Expose
        private String addsLat;
        @SerializedName("adds_long")
        @Expose
        private String addsLong;
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
        @SerializedName("company_logo")
        @Expose
        private String companyLogo;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("avg_rate")
        @Expose
        private String avgRate;

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public String getAddsLat() {
            return addsLat;
        }

        public void setAddsLat(String addsLat) {
            this.addsLat = addsLat;
        }

        public String getAddsLong() {
            return addsLong;
        }

        public void setAddsLong(String addsLong) {
            this.addsLong = addsLong;
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

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAvgRate() {
            return avgRate;
        }

        public void setAvgRate(String avgRate) {
            this.avgRate = avgRate;
        }

    }
}