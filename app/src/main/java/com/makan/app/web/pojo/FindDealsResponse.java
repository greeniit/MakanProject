package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindDealsResponse {

    @SerializedName("property_list")
    @Expose
    private List<OfferProperty> offerProperty = null;
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

    public List<OfferProperty> getOfferProperty() {
        return offerProperty;
    }

    public void setOfferProperty(List<OfferProperty> offerProperty) {
        this.offerProperty = offerProperty;
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

    public class OfferProperty {

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
        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("property_type")
        @Expose
        private String propertyType;
        @SerializedName("offer")
        @Expose
        private String offer;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;

        @SerializedName("favourite")
        @Expose
        private String favourite;

        @SerializedName("bathroom_count")
        @Expose
        private String bathroomcount;

        @SerializedName("bed_count")
        @Expose
        private String bedcount;

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

        public String getOffer() {
            return offer=offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
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


        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public String getBathroomcount() {
            return bathroomcount;
        }

        public void setBathroomcount(String bathroomcount) {
            this.bathroomcount = bathroomcount;
        }

        public String getBedcount() {
            return bedcount;
        }

        public void setBedcount(String bedcount) {
            this.bedcount = bedcount;
        }
    }
}
