package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FillterCategoryResponse {

    @SerializedName("main_category")
    @Expose
    private List<MainCategory> mainCategory = null;
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

    public List<MainCategory> getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(List<MainCategory> mainCategory) {
        this.mainCategory = mainCategory;
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

    public class MainCategory {

        @SerializedName("main_category_id")
        @Expose
        private String mainCategoryId;
        @SerializedName("main_category_name")
        @Expose
        private String mainCategoryName;
        @SerializedName("sub_category_count")
        @Expose
        private String subCategoryCount;
        @SerializedName("sub_category_list")
        @Expose
        private List<SubCategoryList> subCategoryList = null;

        public String getMainCategoryId() {
            return mainCategoryId;
        }

        public void setMainCategoryId(String mainCategoryId) {
            this.mainCategoryId = mainCategoryId;
        }

        public String getMainCategoryName() {
            return mainCategoryName;
        }

        public void setMainCategoryName(String mainCategoryName) {
            this.mainCategoryName = mainCategoryName;
        }

        public String getSubCategoryCount() {
            return subCategoryCount;
        }

        public void setSubCategoryCount(String subCategoryCount) {
            this.subCategoryCount = subCategoryCount;
        }

        public List<SubCategoryList> getSubCategoryList() {
            return subCategoryList;
        }

        public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
            this.subCategoryList = subCategoryList;
        }

    }

    public class SubCategoryList {

        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("sub_category_property_count")
        @Expose
        private String subCategoryPropertyCount;

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCategoryPropertyCount() {
            return subCategoryPropertyCount;
        }

        public void setSubCategoryPropertyCount(String subCategoryPropertyCount) {
            this.subCategoryPropertyCount = subCategoryPropertyCount;
        }

    }

}
