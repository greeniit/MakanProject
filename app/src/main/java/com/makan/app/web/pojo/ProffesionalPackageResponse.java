package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sarath U S, sarath.us44@gmail.com on 2019-09-14.
 */
public class ProffesionalPackageResponse {

    @SerializedName("professional_package")
    @Expose
    public List<ProfessionalPackage> professionalPackage = null;
    @SerializedName("res")
    @Expose
    public Integer res;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("IsSuccess")
    @Expose
    public Integer isSuccess;
    @SerializedName("SessionToken")
    @Expose
    public String sessionToken;

    public List<ProfessionalPackage> getProfessionalPackage() {
        return professionalPackage;
    }

    public void setProfessionalPackage(List<ProfessionalPackage> professionalPackage) {
        this.professionalPackage = professionalPackage;
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

    public class ProfessionalPackage {

        @SerializedName("plan_id")
        @Expose
        public String planId;
        @SerializedName("plan_name")
        @Expose
        public String planName;
        @SerializedName("plan_name_ar")
        @Expose
        public String planNameAr;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("description_ar")
        @Expose
        public String descriptionAr;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("plan_client")
        @Expose
        public String planClient;
        @SerializedName("plan_social_med")
        @Expose
        public String planSocialMed;
        @SerializedName("plan_portal_mob")
        @Expose
        public String planPortalMob;
        @SerializedName("plan_spl_advance")
        @Expose
        public String planSplAdvance;
        @SerializedName("plan_image")
        @Expose
        public String planImage;
        @SerializedName("plan_listing_advance")
        @Expose
        public String planListingAdvance;
        @SerializedName("cost_details")
        @Expose
        public List<CostDetail> costDetails = null;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanNameAr() {
            return planNameAr;
        }

        public void setPlanNameAr(String planNameAr) {
            this.planNameAr = planNameAr;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescriptionAr() {
            return descriptionAr;
        }

        public void setDescriptionAr(String descriptionAr) {
            this.descriptionAr = descriptionAr;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPlanClient() {
            return planClient;
        }

        public void setPlanClient(String planClient) {
            this.planClient = planClient;
        }

        public String getPlanSocialMed() {
            return planSocialMed;
        }

        public void setPlanSocialMed(String planSocialMed) {
            this.planSocialMed = planSocialMed;
        }

        public String getPlanPortalMob() {
            return planPortalMob;
        }

        public void setPlanPortalMob(String planPortalMob) {
            this.planPortalMob = planPortalMob;
        }

        public String getPlanSplAdvance() {
            return planSplAdvance;
        }

        public void setPlanSplAdvance(String planSplAdvance) {
            this.planSplAdvance = planSplAdvance;
        }

        public String getPlanImage() {
            return planImage;
        }

        public void setPlanImage(String planImage) {
            this.planImage = planImage;
        }

        public String getPlanListingAdvance() {
            return planListingAdvance;
        }

        public void setPlanListingAdvance(String planListingAdvance) {
            this.planListingAdvance = planListingAdvance;
        }

        public List<CostDetail> getCostDetails() {
            return costDetails;
        }

        public void setCostDetails(List<CostDetail> costDetails) {
            this.costDetails = costDetails;
        }
    }

        public class CostDetail {

            @SerializedName("plan_cost_id")
            @Expose
            public String planCostId;
            @SerializedName("plan_id")
            @Expose
            public String planId;
            @SerializedName("amount")
            @Expose
            public String amount;
            @SerializedName("duration")
            @Expose
            public String duration;
            @SerializedName("duration_type")
            @Expose
            public String durationType;

            public String getPlanCostId() {
                return planCostId;
            }

            public void setPlanCostId(String planCostId) {
                this.planCostId = planCostId;
            }

            public String getPlanId() {
                return planId;
            }

            public void setPlanId(String planId) {
                this.planId = planId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getDurationType() {
                return durationType;
            }

            public void setDurationType(String durationType) {
                this.durationType = durationType;
            }
        }
    }

