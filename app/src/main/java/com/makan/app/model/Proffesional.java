package com.makan.app.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Proffesional implements Parcelable {


    private String addId;
    private String addsLat;
    private String addsLong;
    private String addsAddress;
    private String addsPhoto;
    private String addsWebsite;
    private String addsDescription;
    private String companyLogo;
    private String companyName;
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

    public Proffesional(Parcel in) {
        this.addId = ((String) in.readValue((String.class.getClassLoader())));
        this.addsLat = ((String) in.readValue((String.class.getClassLoader())));
        this.addsLong = ((String) in.readValue((String.class.getClassLoader())));
        this.addsAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.addsPhoto = ((String) in.readValue((String.class.getClassLoader())));
        this.addsWebsite = ((String) in.readValue((String.class.getClassLoader())));
        this.addsDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.companyLogo = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.avgRate = ((String) in.readValue((String.class.getClassLoader())));
    }


    public static final Creator<Proffesional> CREATOR = new Creator<Proffesional>() {
        @Override
        public Proffesional createFromParcel(Parcel in) {
            return new Proffesional(in);
        }

        @Override
        public Proffesional[] newArray(int size) {
            return new Proffesional[size];
        }
    };

    public Proffesional(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(addId);
        dest.writeValue(addsLat);
        dest.writeValue(addsLong);
        dest.writeValue(addsAddress);
        dest.writeValue(addsPhoto);
        dest.writeValue(addsWebsite);
        dest.writeValue(addsDescription);
        dest.writeValue(companyLogo);
        dest.writeValue(companyName);
        dest.writeValue(avgRate);
    }
}
