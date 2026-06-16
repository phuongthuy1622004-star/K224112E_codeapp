package com.daothiphuongthuy.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    private String orderId;
    private String employeeId;
    private String cusId;
    private Date orderDate;
    private  OrderStatus orderStatus;

    static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");

    public String toString() {
        String data = orderId + "\t" + sdf.format(orderDate) + "\t" + DataWarehouse.sumOfMoney(this);
        return data;
    }

    public Order(String orderId, String employeeId, String cusId, Date orderDate, OrderStatus orderStatus) {
        this(orderId, employeeId, cusId, orderDate);
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Order(String orderId, String employeeId, String cusId, Date orderDate) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.cusId = cusId;
        this.orderDate = orderDate;
    }

    public Order() {
    }
}
