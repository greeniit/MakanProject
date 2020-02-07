package com.makan.app.web.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimilarDataRequest{

        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("property_id")
        @Expose
        private String propertyId;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitud() {
            return longitud;
        }

        public void setLongitud(String longitud) {
            this.longitud = longitud;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

}