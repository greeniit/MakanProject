package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sarath U S, sarath.us44@gmail.com on 2019-09-14.
 */
public class AgencyPackageResponse {

    @SerializedName("agency_package")
    @Expose
    private List<AgencyPackage> agencyPackage = null;
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

    public List<AgencyPackage> getAgencyPackage() {
        return agencyPackage;
    }

    public void setAgencyPackage(List<AgencyPackage> agencyPackage) {
        this.agencyPackage = agencyPackage;
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

    public class CostDetail {

        @SerializedName("plan_cost_id")
        @Expose
        private String planCostId;
        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("duration_type")
        @Expose
        private String durationType;

        public String getPlanCostId() {
            return planCostId;
        }

        public void setPlanCostId(String planCostId) {
            this.planCostId = planCostId;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDurationType() {
            return durationType;
        }

        public void setDurationType(String durationType) {
            this.durationType = durationType;
        }

    }
    public class AgencyPackage {

        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("plan_name")
        @Expose
        private String planName;
        @SerializedName("plan_name_ar")
        @Expose
        private String planNameAr;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("description_ar")
        @Expose
        private String descriptionAr;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("no_of_ppty")
        @Expose
        private String noOfPpty;
        @SerializedName("featured_listing")
        @Expose
        private String featuredListing;
        @SerializedName("adevrtisment")
        @Expose
        private String adevrtisment;
        @SerializedName("post_req_mail")
        @Expose
        private String postReqMail;
        @SerializedName("miscellaneous")
        @Expose
        private Object miscellaneous;
        @SerializedName("training")
        @Expose
        private Object training;
        @SerializedName("cost_details")
        @Expose
        private List<CostDetail> costDetails = null;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanNameAr() {
            return planNameAr;
        }

        public void setPlanNameAr(String planNameAr) {
            this.planNameAr = planNameAr;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescriptionAr() {
            return descriptionAr;
        }

        public void setDescriptionAr(String descriptionAr) {
            this.descriptionAr = descriptionAr;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNoOfPpty() {
            return noOfPpty;
        }

        public void setNoOfPpty(String noOfPpty) {
            this.noOfPpty = noOfPpty;
        }

        public String getFeaturedListing() {
            return featuredListing;
        }

        public void setFeaturedListing(String featuredListing) {
            this.featuredListing = featuredListing;
        }

        public String getAdevrtisment() {
            return adevrtisment;
        }

        public void setAdevrtisment(String adevrtisment) {
            this.adevrtisment = adevrtisment;
        }

        public String getPostReqMail() {
            return postReqMail;
        }

        public void setPostReqMail(String postReqMail) {
            this.postReqMail = postReqMail;
        }

        public Object getMiscellaneous() {
            return miscellaneous;
        }

        public void setMiscellaneous(Object miscellaneous) {
            this.miscellaneous = miscellaneous;
        }

        public Object getTraining() {
            return training;
        }

        public void setTraining(Object training) {
            this.training = training;
        }

        public List<CostDetail> getCostDetails() {
            return costDetails;
        }

        public void setCostDetails(List<CostDetail> costDetails) {
            this.costDetails = costDetails;
        }

    }
}

