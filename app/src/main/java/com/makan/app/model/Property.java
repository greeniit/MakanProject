package com.makan.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Property implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String address;
    private int bedCount;
    private int area;
    private String price;
    private int thumbnail;
    private String image;
    private String propertyType;
    private LatLng latLng;
    private String offerPrice;
    private String offerPercentage;
    private String favourite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
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
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.address);
        dest.writeInt(this.bedCount);
        dest.writeInt(this.area);
        dest.writeString(this.price);
        dest.writeInt(this.thumbnail);
        dest.writeString(this.image);
        dest.writeString(this.propertyType);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.offerPrice);
        dest.writeString(this.offerPercentage);
        dest.writeString(this.favourite);
    }

    public Property() {
    }

    protected Property(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.address = in.readString();
        this.bedCount = in.readInt();
        this.area = in.readInt();
        this.price = in.readString();
        this.thumbnail = in.readInt();
        this.image = in.readString();
        this.propertyType = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.offerPrice = in.readString();
        this.offerPercentage = in.readString();
        this.favourite = in.readString();
    }

    public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel source) {
            return new Property(source);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };
}
