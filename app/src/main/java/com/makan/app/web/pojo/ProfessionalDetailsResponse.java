package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionalDetailsResponse {

    @SerializedName("professional")
    @Expose
    private List<Professional> professional = null;
    @SerializedName("banner_gallery")
    @Expose
    private List<BannerGallery> bannerGallery = null;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;
    @SerializedName("rating")
    @Expose
    private List<Rating> rating = null;
    @SerializedName("clients")
    @Expose
    private List<Client> clients = null;
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

    public List<Professional> getProfessional() {
        return professional;
    }

    public void setProfessional(List<Professional> professional) {
        this.professional = professional;
    }

    public List<BannerGallery> getBannerGallery() {
        return bannerGallery;
    }

    public void setBannerGallery(List<BannerGallery> bannerGallery) {
        this.bannerGallery = bannerGallery;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
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
    public class BannerGallery {

        @SerializedName("adds_img_id")
        @Expose
        private String addsImgId;
        @SerializedName("adds_id")
        @Expose
        private String addsId;
        @SerializedName("adds_img")
        @Expose
        private String addsImg;
        @SerializedName("image_title")
        @Expose
        private String imageTitle;
        @SerializedName("description")
        @Expose
        private String description;

        public String getAddsImgId() {
            return addsImgId;
        }

        public void setAddsImgId(String addsImgId) {
            this.addsImgId = addsImgId;
        }

        public String getAddsId() {
            return addsId;
        }

        public void setAddsId(String addsId) {
            this.addsId = addsId;
        }

        public String getAddsImg() {
            return addsImg;
        }

        public void setAddsImg(String addsImg) {
            this.addsImg = addsImg;
        }

        public String getImageTitle() {
            return imageTitle;
        }

        public void setImageTitle(String imageTitle) {
            this.imageTitle = imageTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class Client {

        @SerializedName("adds_client_id")
        @Expose
        private String addsClientId;
        @SerializedName("adds_id")
        @Expose
        private String addsId;
        @SerializedName("client_name")
        @Expose
        private String clientName;
        @SerializedName("client_logo")
        @Expose
        private String clientLogo;

        public String getAddsClientId() {
            return addsClientId;
        }

        public void setAddsClientId(String addsClientId) {
            this.addsClientId = addsClientId;
        }

        public String getAddsId() {
            return addsId;
        }

        public void setAddsId(String addsId) {
            this.addsId = addsId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientLogo() {
            return clientLogo;
        }

        public void setClientLogo(String clientLogo) {
            this.clientLogo = clientLogo;
        }

    }

    public class Gallery {

        @SerializedName("adds_img")
        @Expose
        private String addsImg;
        @SerializedName("image_title")
        @Expose
        private String imageTitle;
        @SerializedName("description")
        @Expose
        private String description;

        public String getAddsImg() {
            return addsImg;
        }

        public void setAddsImg(String addsImg) {
            this.addsImg = addsImg;
        }

        public String getImageTitle() {
            return imageTitle;
        }

        public void setImageTitle(String imageTitle) {
            this.imageTitle = imageTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class Professional {

        @SerializedName("adds_address")
        @Expose
        private String addsAddress;
        @SerializedName("adds_long")
        @Expose
        private String addsLong;
        @SerializedName("adds_lat")
        @Expose
        private String addsLat;
        @SerializedName("adds_photo")
        @Expose
        private String addsPhoto;
        @SerializedName("adds_website")
        @Expose
        private String addsWebsite;
        @SerializedName("adds_description")
        @Expose
        private String addsDescription;
        @SerializedName("company_logo")
        @Expose
        private String companyLogo;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("add_id")
        @Expose
        private String addId;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("service_icon")
        @Expose
        private String serviceIcon;

        public String getAddsAddress() {
            return addsAddress;
        }

        public void setAddsAddress(String addsAddress) {
            this.addsAddress = addsAddress;
        }

        public String getAddsLong() {
            return addsLong;
        }

        public void setAddsLong(String addsLong) {
            this.addsLong = addsLong;
        }

        public String getAddsLat() {
            return addsLat;
        }

        public void setAddsLat(String addsLat) {
            this.addsLat = addsLat;
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

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceIcon() {
            return serviceIcon;
        }

        public void setServiceIcon(String serviceIcon) {
            this.serviceIcon = serviceIcon;
        }

    }

    public class Rating {

        @SerializedName("professional_review_id")
        @Expose
        private String professionalReviewId;
        @SerializedName("adds_id")
        @Expose
        private String addsId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("rate")
        @Expose
        private String rate;
        @SerializedName("comment")
        @Expose
        private String comment;
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
        private String ageGroup;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("u_fname")
        @Expose
        private String uFname;
        @SerializedName("u_lname")
        @Expose
        private String uLname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("home_address")
        @Expose
        private Object homeAddress;

        public String getProfessionalReviewId() {
            return professionalReviewId;
        }

        public void setProfessionalReviewId(String professionalReviewId) {
            this.professionalReviewId = professionalReviewId;
        }

        public String getAddsId() {
            return addsId;
        }

        public void setAddsId(String addsId) {
            this.addsId = addsId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

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

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getUFname() {
            return uFname;
        }

        public void setUFname(String uFname) {
            this.uFname = uFname;
        }

        public String getULname() {
            return uLname;
        }

        public void setULname(String uLname) {
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