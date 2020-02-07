package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialMediaResponse {


    @SerializedName("user_details")
    @Expose
    private List<UserDetails> userDetails = null;
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

    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetails> userDetails) {
        this.userDetails = userDetails;
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


    public class UserDetails {

        @SerializedName("U_id")
        @Expose
        private String uId;
        @SerializedName("profile_image")
        @Expose
        private Object profileImage;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("age_group")
        @Expose
        private Object ageGroup;
        @SerializedName("country")
        @Expose
        private Object country;
        @SerializedName("u_fname")
        @Expose
        private Object uFname;
        @SerializedName("u_lname")
        @Expose
        private Object uLname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("home_address")
        @Expose
        private Object homeAddress;

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public Object getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(Object profileImage) {
            this.profileImage = profileImage;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Object getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(Object ageGroup) {
            this.ageGroup = ageGroup;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getUFname() {
            return uFname;
        }

        public void setUFname(Object uFname) {
            this.uFname = uFname;
        }

        public Object getULname() {
            return uLname;
        }

        public void setULname(Object uLname) {
            this.uLname = uLname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public Object getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(Object homeAddress) {
            this.homeAddress = homeAddress;
        }



    }
}
