package com.makan.app.web.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SocialMediaRequest
{
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("telephone")
    @Expose
    private String telephone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
