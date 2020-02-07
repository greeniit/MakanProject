package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalSearchRequest {


    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("service_location")
    @Expose
    private String servicelocation;
    @SerializedName("service_id")
    @Expose
    private String serviceid;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getServicelocation() {
        return servicelocation;
    }

    public void setServicelocation(String servicelocation) {
        this.servicelocation = servicelocation;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }
}
