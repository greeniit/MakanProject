package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyList {

    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("property_name")
    @Expose
    private String propertyName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("plot_area")
    @Expose
    private String plotArea;
    @SerializedName("plot_area_unit")
    @Expose
    private String plotAreaUnit;
    @SerializedName("building_area")
    @Expose
    private String buildingArea;
    @SerializedName("building_area_unit")
    @Expose
    private String buildingAreaUnit;
    @SerializedName("rooms")
    @Expose
    private String rooms;
    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("main_category_name")
    @Expose
    private String mainCategoryName;

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("property_type")
    @Expose
    private String propertyType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("agency_name")
    @Expose
    private String agencyName;
    @SerializedName("agency_logo")
    @Expose
    private String agencyLogo;
    @SerializedName("agency_email")
    @Expose
    private String agencyEmail;
    @SerializedName("agency_contact_no")
    @Expose
    private String agencyContactNo;
    @SerializedName("agency_address")
    @Expose
    private String agencyAddress;
    @SerializedName("agency_website")
    @Expose
    private String agencyWebsite;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;

    @SerializedName("favourite")
    @Expose
    private String favourite;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(String plotArea) {
        this.plotArea = plotArea;
    }

    public String getPlotAreaUnit() {
        return plotAreaUnit;
    }

    public void setPlotAreaUnit(String plotAreaUnit) {
        this.plotAreaUnit = plotAreaUnit;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getBuildingAreaUnit() {
        return buildingAreaUnit;
    }

    public void setBuildingAreaUnit(String buildingAreaUnit) {
        this.buildingAreaUnit = buildingAreaUnit;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyLogo() {
        return agencyLogo;
    }

    public void setAgencyLogo(String agencyLogo) {
        this.agencyLogo = agencyLogo;
    }

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail;
    }

    public String getAgencyContactNo() {
        return agencyContactNo;
    }

    public void setAgencyContactNo(String agencyContactNo) {
        this.agencyContactNo = agencyContactNo;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public String getAgencyWebsite() {
        return agencyWebsite;
    }

    public void setAgencyWebsite(String agencyWebsite) {
        this.agencyWebsite = agencyWebsite;
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

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }
}
