package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostYourReqRequest {

    @SerializedName("reqDescription")
    @Expose
    private String reqDescription;
    @SerializedName("age_of_con")
    @Expose
    private String ageOfCon;
    @SerializedName("states")
    @Expose
    private String states;
    @SerializedName("minprice")
    @Expose
    private String minprice;
    @SerializedName("maxprice")
    @Expose
    private String maxprice;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("maxarea")
    @Expose
    private String maxarea;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("governorates")
    @Expose
    private String governorates;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("minarea")
    @Expose
    private String minarea;
    @SerializedName("bathroom")
    @Expose
    private String bathroom;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("bedrooms")
    @Expose
    private String bedrooms;
    @SerializedName("name")
    @Expose
    private String name;

    public String getReqDescription() {
        return reqDescription;
    }

    public void setReqDescription(String reqDescription) {
        this.reqDescription = reqDescription;
    }

    public String getAgeOfCon() {
        return ageOfCon;
    }

    public void setAgeOfCon(String ageOfCon) {
        this.ageOfCon = ageOfCon;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMaxarea() {
        return maxarea;
    }

    public void setMaxarea(String maxarea) {
        this.maxarea = maxarea;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getGovernorates() {
        return governorates;
    }

    public void setGovernorates(String governorates) {
        this.governorates = governorates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMinarea() {
        return minarea;
    }

    public void setMinarea(String minarea) {
        this.minarea = minarea;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
