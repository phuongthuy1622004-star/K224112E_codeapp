package com.daothiphuongthuy.models;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private String cusId;
    private String cusName;
    private String cusPhone;
    private String cusMail;
    private String cusAddress;
    private Date cusBirthday;

    @Override
    public String toString() {
        return "Customer{" +
                "cusId='" + cusId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusPhone='" + cusPhone + '\'' +
                ", cusMail='" + cusMail + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusBirthday=" + cusBirthday +
                '}';
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusMail() {
        return cusMail;
    }

    public void setCusMail(String cusMail) {
        this.cusMail = cusMail;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public Date getCusBirthday() {
        return cusBirthday;
    }

    public void setCusBirthday(Date cusBirthday) {
        this.cusBirthday = cusBirthday;
    }

    public Customer(String cusId, String cusName, String cusPhone, String cusMail, String cusAddress, Date cusBirthday) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusMail = cusMail;
        this.cusAddress = cusAddress;
        this.cusBirthday = cusBirthday;
    }

    public Customer() {
    }
}
