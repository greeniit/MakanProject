package com.makan.app.web.pojo;

import java.util.List;

/**
 * Created by Sarath U S, sarath.us44@gmail.com on 2020-02-05.
 */

public class ProfessionalDetailsModel {


    /**
     * professional : [{"adds_id":"2149","adds_address":"Ad Dakhiliyah \u200dGovernorate, Oman","adds_photo":"6ee911d8d546ab2b54ecff8161d512bc.png","adds_website":"","adds_description":"MRJ Construction","adds_long":"58","adds_lat":"22","company_logo":"2a68a5fa8feb502f651ddc945e79ff37.png","company_name":"MRJ البناء","avg_rate":"4.0000"},{"adds_id":"2166","adds_address":"محافظة الداخلية ، عمان","adds_photo":"36d070f8407959d3fdd2ac07df4dbfca.jpg","adds_website":"","adds_description":"تأسست شركة TOCO في عام 1965 ، وقد بنت بفخر أول خط انسيابي ، أول خط أنابيب ، أول مزرعة للدبابات ، أول محطة إنتاج ، أول خط كهرباء علوي وأجرت أول منصة تلاعب في سلطنة عمان.","adds_long":"58","adds_lat":"22","company_logo":"c0a80e5f0b74594f523be4fe3a2bcfa8.jpg","company_name":"Smartsheet BrandVoice","avg_rate":"0"},{"adds_id":"2167","adds_address":"محافظة الوسطى ، عمان","adds_photo":"6cba6a59c41d79ee9f0289d00d3cbb4d.jpg","adds_website":"","adds_description":"شركة الإنشاءات المحدودة (TOCO) هي واحدة من أقدم ومقدمي خدمات النفط والغاز الأكثر شهرة في سلطنة عمان.","adds_long":"53.679725646972656","adds_lat":"17.25184355307685","company_logo":"7e77b8e88fa9cdef8d06f32bc2ebe4ea.jpg","company_name":"شركة البناء ذ م م (TOCO)","avg_rate":"3.5000"},{"adds_id":"2363","adds_address":"","adds_photo":null,"adds_website":"sad","adds_description":null,"adds_long":"","adds_lat":"","company_logo":"","company_name":"","avg_rate":"0"},{"adds_id":"2426","adds_address":"Dhofar Governorate, Oman","adds_photo":"d93174ca86b20880ae95ace05bf54371.jpg","adds_website":"www.binhamood.com","adds_description":"خدمات بن حمود للخدمات النفطية شركة بن حمود لخدمات حقول النفط هي شركة عمانية تأسست في عام 2017 بهدف توفير المنتجات والخدمات لصناعة النفط والغاز في سلطنة عمان. بفضل بنطاقتها الواسعة من الخدمات ، فإن بن حمود قادر على تقديم منتجات وخدمات رائعة لشركتك. كما نقدم خدمات تأجير معدات حقول النفط المختلفة.","adds_long":"54.168617248535156","adds_lat":"18.63147652118786","company_logo":"7886229310e846be21801575f685bca1.jpg","company_name":"حمود المعمري","avg_rate":"5.0000"}]
     * res : 1
     * msg : True
     * IsSuccess : 1
     * SessionToken : True
     */

    private int res;
    private String msg;
    private int IsSuccess;
    private String SessionToken;
    private List<ProfessionalBean> professional;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(int IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String SessionToken) {
        this.SessionToken = SessionToken;
    }

    public List<ProfessionalBean> getProfessional() {
        return professional;
    }

    public void setProfessional(List<ProfessionalBean> professional) {
        this.professional = professional;
    }

    public static class ProfessionalBean {
        /**
         * adds_id : 2149
         * adds_address : Ad Dakhiliyah ‍Governorate, Oman
         * adds_photo : 6ee911d8d546ab2b54ecff8161d512bc.png
         * adds_website :
         * adds_description : MRJ Construction
         * adds_long : 58
         * adds_lat : 22
         * company_logo : 2a68a5fa8feb502f651ddc945e79ff37.png
         * company_name : MRJ البناء
         * avg_rate : 4.0000
         */

        private String adds_id;
        private String adds_address;
        private String adds_photo;
        private String adds_website;
        private String adds_description;
        private String adds_long;
        private String adds_lat;
        private String company_logo;
        private String company_name;
        private String avg_rate;

        public String getAdds_id() {
            return adds_id;
        }

        public void setAdds_id(String adds_id) {
            this.adds_id = adds_id;
        }

        public String getAdds_address() {
            return adds_address;
        }

        public void setAdds_address(String adds_address) {
            this.adds_address = adds_address;
        }

        public String getAdds_photo() {
            return adds_photo;
        }

        public void setAdds_photo(String adds_photo) {
            this.adds_photo = adds_photo;
        }

        public String getAdds_website() {
            return adds_website;
        }

        public void setAdds_website(String adds_website) {
            this.adds_website = adds_website;
        }

        public String getAdds_description() {
            return adds_description;
        }

        public void setAdds_description(String adds_description) {
            this.adds_description = adds_description;
        }

        public String getAdds_long() {
            return adds_long;
        }

        public void setAdds_long(String adds_long) {
            this.adds_long = adds_long;
        }

        public String getAdds_lat() {
            return adds_lat;
        }

        public void setAdds_lat(String adds_lat) {
            this.adds_lat = adds_lat;
        }

        public String getCompany_logo() {
            return company_logo;
        }

        public void setCompany_logo(String company_logo) {
            this.company_logo = company_logo;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getAvg_rate() {
            return avg_rate;
        }

        public void setAvg_rate(String avg_rate) {
            this.avg_rate = avg_rate;
        }
    }
}
