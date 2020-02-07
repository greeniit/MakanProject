package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class NewFilterRequest {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("selectedType")
    @Expose
    private String selectedType;
    @SerializedName("sub_category_id")
    @Expose
    private ArrayList<String> subCategoryId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("property_name")
    @Expose
    private String propertyName;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("minarea")
    @Expose
    private String minarea;
    @SerializedName("maxarea")
    @Expose
    private String maxarea;
    @SerializedName("minprice")
    @Expose
    private String minprice;
    @SerializedName("maxprice")
    @Expose
    private String maxprice;
    @SerializedName("bed_count")
    @Expose
    private String bedCount;
    @SerializedName("bath_count")
    @Expose
    private String bathCount;
    @SerializedName("rent_type")
    @Expose
    private String rentType;

    @SerializedName("user_id")
    @Expose
    private String userid;



    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public ArrayList<String> getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(ArrayList<String> subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getMinarea() {
        return minarea;
    }

    public void setMinarea(String minarea) {
        this.minarea = minarea;
    }

    public String getMaxarea() {
        return maxarea;
    }

    public void setMaxarea(String maxarea) {
        this.maxarea = maxarea;
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

    public String getBedCount() {
        return bedCount;
    }

    public void setBedCount(String bedCount) {
        this.bedCount = bedCount;
    }

    public String getBathCount() {
        return bathCount;
    }

    public void setBathCount(String bathCount) {
        this.bathCount = bathCount;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
