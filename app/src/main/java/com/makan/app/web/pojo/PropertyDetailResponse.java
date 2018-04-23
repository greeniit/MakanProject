package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyDetailResponse {

    @SerializedName("property_list")
    @Expose
    private List<PropertyList> propertyList = null;
    @SerializedName("propertydetail_images")
    @Expose
    private List<PropertydetailImage> propertydetailImages = null;
    @SerializedName("propertydetail_amenities")
    @Expose
    private List<PropertydetailAmenity> propertydetailAmenities = null;
    @SerializedName("total_amenities")
    @Expose
    private List<TotalAmenity> totalAmenities = null;
    @SerializedName("total_amenities_count")
    @Expose
    private Integer totalAmenitiesCount;
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

    public List<PropertyList> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<PropertyList> propertyList) {
        this.propertyList = propertyList;
    }

    public List<PropertydetailImage> getPropertydetailImages() {
        return propertydetailImages;
    }

    public void setPropertydetailImages(List<PropertydetailImage> propertydetailImages) {
        this.propertydetailImages = propertydetailImages;
    }

    public List<PropertydetailAmenity> getPropertydetailAmenities() {
        return propertydetailAmenities;
    }

    public void setPropertydetailAmenities(List<PropertydetailAmenity> propertydetailAmenities) {
        this.propertydetailAmenities = propertydetailAmenities;
    }

    public List<TotalAmenity> getTotalAmenities() {
        return totalAmenities;
    }

    public void setTotalAmenities(List<TotalAmenity> totalAmenities) {
        this.totalAmenities = totalAmenities;
    }

    public Integer getTotalAmenitiesCount() {
        return totalAmenitiesCount;
    }

    public void setTotalAmenitiesCount(Integer totalAmenitiesCount) {
        this.totalAmenitiesCount = totalAmenitiesCount;
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


    public static class PropertydetailAmenity {

        @SerializedName("near_by_id")
        @Expose
        private String nearById;
        @SerializedName("near_by_name")
        @Expose
        private String nearByName;
        @SerializedName("near_by_icon")
        @Expose
        private String nearByIcon;

        public String getNearById() {
            return nearById;
        }

        public void setNearById(String nearById) {
            this.nearById = nearById;
        }

        public String getNearByName() {
            return nearByName;
        }

        public void setNearByName(String nearByName) {
            this.nearByName = nearByName;
        }

        public String getNearByIcon() {
            return nearByIcon;
        }

        public void setNearByIcon(String nearByIcon) {
            this.nearByIcon = nearByIcon;
        }

    }

    public static class PropertydetailImage {

        @SerializedName("detail_images_id")
        @Expose
        private String detailImagesId;
        @SerializedName("detail_id")
        @Expose
        private String detailId;
        @SerializedName("detail_images")
        @Expose
        private String detailImages;
        @SerializedName("detail_images_description")
        @Expose
        private Object detailImagesDescription;

        public String getDetailImagesId() {
            return detailImagesId;
        }

        public void setDetailImagesId(String detailImagesId) {
            this.detailImagesId = detailImagesId;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getDetailImages() {
            return detailImages;
        }

        public void setDetailImages(String detailImages) {
            this.detailImages = detailImages;
        }

        public Object getDetailImagesDescription() {
            return detailImagesDescription;
        }

        public void setDetailImagesDescription(Object detailImagesDescription) {
            this.detailImagesDescription = detailImagesDescription;
        }

    }

    public class TotalAmenity {

        @SerializedName("near_by_id")
        @Expose
        private String nearById;
        @SerializedName("near_by_name")
        @Expose
        private String nearByName;
        @SerializedName("near_by_icon")
        @Expose
        private String nearByIcon;

        public String getNearById() {
            return nearById;
        }

        public void setNearById(String nearById) {
            this.nearById = nearById;
        }

        public String getNearByName() {
            return nearByName;
        }

        public void setNearByName(String nearByName) {
            this.nearByName = nearByName;
        }

        public String getNearByIcon() {
            return nearByIcon;
        }

        public void setNearByIcon(String nearByIcon) {
            this.nearByIcon = nearByIcon;
        }
    }
}
