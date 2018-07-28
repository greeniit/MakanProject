package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPropertyByTypeRequest {

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("selectedType")
    @Expose
    private Integer selectedType;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;
    @SerializedName("currentPageno")
    @Expose
    private Integer currentPageno;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Integer selectedType) {
        this.selectedType = selectedType;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getCurrentPageno() {
        return currentPageno;
    }

    public void setCurrentPageno(Integer currentPageno) {
        this.currentPageno = currentPageno;
    }
}
