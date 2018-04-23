package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("review_service")
    @Expose
    private String reviewService;
    @SerializedName("review_offer")
    @Expose
    private String reviewOffer;
    @SerializedName("review_price")
    @Expose
    private String reviewPrice;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewService() {
        return reviewService;
    }

    public void setReviewService(String reviewService) {
        this.reviewService = reviewService;
    }

    public String getReviewOffer() {
        return reviewOffer;
    }

    public void setReviewOffer(String reviewOffer) {
        this.reviewOffer = reviewOffer;
    }

    public String getReviewPrice() {
        return reviewPrice;
    }

    public void setReviewPrice(String reviewPrice) {
        this.reviewPrice = reviewPrice;
    }
}
