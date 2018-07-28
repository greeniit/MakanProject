package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer {

    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("user_id")
    @Expose
    private String userId;
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
    @SerializedName("agency_file")
    @Expose
    private Object agencyFile;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("agency_description")
    @Expose
    private String agencyDescription;
    @SerializedName("agency_long")
    @Expose
    private String agencyLong;
    @SerializedName("agency_lat")
    @Expose
    private String agencyLat;
    @SerializedName("agency_template")
    @Expose
    private String agencyTemplate;
    @SerializedName("amenities")
    @Expose
    private String amenities;
    @SerializedName("approve_status")
    @Expose
    private String approveStatus;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Object getAgencyFile() {
        return agencyFile;
    }

    public void setAgencyFile(Object agencyFile) {
        this.agencyFile = agencyFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgencyDescription() {
        return agencyDescription;
    }

    public void setAgencyDescription(String agencyDescription) {
        this.agencyDescription = agencyDescription;
    }

    public String getAgencyLong() {
        return agencyLong;
    }

    public void setAgencyLong(String agencyLong) {
        this.agencyLong = agencyLong;
    }

    public String getAgencyLat() {
        return agencyLat;
    }

    public void setAgencyLat(String agencyLat) {
        this.agencyLat = agencyLat;
    }

    public String getAgencyTemplate() {
        return agencyTemplate;
    }

    public void setAgencyTemplate(String agencyTemplate) {
        this.agencyTemplate = agencyTemplate;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

}
