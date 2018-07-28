package com.makan.app.web.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookPropertyRequest {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("agree")
    @Expose
    private Integer agree;
    @SerializedName("loan")
    @Expose
    private Integer loan;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getLoan() {
        return loan;
    }

    public void setLoan(Integer loan) {
        this.loan = loan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
