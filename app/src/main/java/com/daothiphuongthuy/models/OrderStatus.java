package com.daothiphuongthuy.models;

public enum OrderStatus {
    ALL ("Tất cả các loại hóa đơn"),
    COMPLETED ("Các hóa đơn đã hoàn tất hành trình"),
    NOT_YET_PAYMENT ("Hóa đơn chưa thanh toán"),
    GOING_LOGISTIC ("Hóa đơn đang xử lý logistic"),
    CUSTOMER_COMPLAIN ("Hóa đơn bị khách hàng la lối um xùm");

    private String description;
    private OrderStatus(String description)
    {
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }
}
