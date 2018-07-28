package com.makan.app.web.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {

    @SerializedName("total_buy_items_count")
    @Expose
    private Integer totalBuyItemsCount;
    @SerializedName("total_rent_items_count")
    @Expose
    private Integer totalRentItemsCount;
    @SerializedName("featured_property")
    @Expose
    private List<RecentProperty> featuredProperty;

    @SerializedName("slider_images")
    @Expose
    private List<SliderImage> sliderImages = null;

    @SerializedName("total_featured_property_count")
    @Expose
    private Integer totalFeaturedPropertyCount;
    @SerializedName("recent_property")
    @Expose
    private List<RecentProperty> recentProperty = null;
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

    public Integer getTotalBuyItemsCount() {
        return totalBuyItemsCount;
    }

    public void setTotalBuyItemsCount(Integer totalBuyItemsCount) {
        this.totalBuyItemsCount = totalBuyItemsCount;
    }

    public Integer getTotalRentItemsCount() {
        return totalRentItemsCount;
    }

    public void setTotalRentItemsCount(Integer totalRentItemsCount) {
        this.totalRentItemsCount = totalRentItemsCount;
    }

    public List<RecentProperty> getFeaturedProperty() {
        return featuredProperty;
    }

    public void setFeaturedProperty(List<RecentProperty> featuredProperty) {
        this.featuredProperty = featuredProperty;
    }

    public Integer getTotalFeaturedPropertyCount() {
        return totalFeaturedPropertyCount;
    }

    public void setTotalFeaturedPropertyCount(Integer totalFeaturedPropertyCount) {
        this.totalFeaturedPropertyCount = totalFeaturedPropertyCount;
    }

    public List<RecentProperty> getRecentProperty() {
        return recentProperty;
    }

    public void setRecentProperty(List<RecentProperty> recentProperty) {
        this.recentProperty = recentProperty;
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

    public List<SliderImage> getSliderImages() {
        return sliderImages;
    }

    public void setSliderImages(List<SliderImage> sliderImages) {
        this.sliderImages = sliderImages;
    }

    public static class RecentProperty implements Parcelable {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.propertyId);
            dest.writeString(this.propertyName);
            dest.writeString(this.location);
            dest.writeString(this.image);
            dest.writeString(this.plotArea);
            dest.writeString(this.plotAreaUnit);
            dest.writeString(this.buildingArea);
            dest.writeString(this.buildingAreaUnit);
            dest.writeString(this.rooms);
            dest.writeString(this.price);
            dest.writeString(this.subCategoryId);
            dest.writeString(this.subCategoryName);
            dest.writeString(this.propertyType);
            dest.writeString(this.lat);
            dest.writeString(this._long);
            dest.writeString(this.favourite);
        }

        public RecentProperty() {
        }

        protected RecentProperty(Parcel in) {
            this.propertyId = in.readString();
            this.propertyName = in.readString();
            this.location = in.readString();
            this.image = in.readString();
            this.plotArea = in.readString();
            this.plotAreaUnit = in.readString();
            this.buildingArea = in.readString();
            this.buildingAreaUnit = in.readString();
            this.rooms = in.readString();
            this.price = in.readString();
            this.subCategoryId = in.readString();
            this.subCategoryName = in.readString();
            this.propertyType = in.readString();
            this.lat = in.readString();
            this._long = in.readString();
            this.favourite = in.readString();
        }

        public static final Parcelable.Creator<RecentProperty> CREATOR = new Parcelable.Creator<RecentProperty>() {
            @Override
            public RecentProperty createFromParcel(Parcel source) {
                return new RecentProperty(source);
            }

            @Override
            public RecentProperty[] newArray(int size) {
                return new RecentProperty[size];
            }
        };
    }

    public class SliderImage {

        @SerializedName("prefer_cities_id")
        @Expose
        private String preferCitiesId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("citie_name")
        @Expose
        private String citieName;
        @SerializedName("count")
        @Expose
        private String count;

        public String getPreferCitiesId() {
            return preferCitiesId;
        }

        public void setPreferCitiesId(String preferCitiesId) {
            this.preferCitiesId = preferCitiesId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCitieName() {
            return citieName;
        }

        public void setCitieName(String citieName) {
            this.citieName = citieName;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}
