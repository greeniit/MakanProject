package com.makan.app.web.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FilterSearchRequest implements Parcelable {

    @SerializedName("selectedType")
    @Expose
    private Integer selectedType;
    @SerializedName("sub_category_id")
    @Expose
    private List<Integer> subCategoryId = null;
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
    private Integer minarea;
    @SerializedName("maxarea")
    @Expose
    private Integer maxarea;
    @SerializedName("minprice")
    @Expose
    private Integer minprice;
    @SerializedName("maxprice")
    @Expose
    private Integer maxprice;

    public Integer getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Integer selectedType) {
        this.selectedType = selectedType;
    }

    public List<Integer> getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(List<Integer> subCategoryId) {
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

    public Integer getMinarea() {
        return minarea;
    }

    public void setMinarea(Integer minarea) {
        this.minarea = minarea;
    }

    public Integer getMaxarea() {
        return maxarea;
    }

    public void setMaxarea(Integer maxarea) {
        this.maxarea = maxarea;
    }

    public Integer getMinprice() {
        return minprice;
    }

    public void setMinprice(Integer minprice) {
        this.minprice = minprice;
    }

    public Integer getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(Integer maxprice) {
        this.maxprice = maxprice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.selectedType);
        dest.writeList(this.subCategoryId);
        dest.writeString(this.location);
        dest.writeString(this.propertyName);
        dest.writeString(this.lat);
        dest.writeString(this._long);
        dest.writeValue(this.minarea);
        dest.writeValue(this.maxarea);
        dest.writeValue(this.minprice);
        dest.writeValue(this.maxprice);
    }

    public FilterSearchRequest() {
    }

    protected FilterSearchRequest(Parcel in) {
        this.selectedType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subCategoryId = new ArrayList<Integer>();
        in.readList(this.subCategoryId, Integer.class.getClassLoader());
        this.location = in.readString();
        this.propertyName = in.readString();
        this.lat = in.readString();
        this._long = in.readString();
        this.minarea = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxarea = (Integer) in.readValue(Integer.class.getClassLoader());
        this.minprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxprice = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<FilterSearchRequest> CREATOR = new Parcelable.Creator<FilterSearchRequest>() {
        @Override
        public FilterSearchRequest createFromParcel(Parcel source) {
            return new FilterSearchRequest(source);
        }

        @Override
        public FilterSearchRequest[] newArray(int size) {
            return new FilterSearchRequest[size];
        }
    };
}
