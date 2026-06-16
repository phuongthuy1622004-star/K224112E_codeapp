package com.daothiphuongthuy.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String productName;

    private int quantity;
    private double price;
    private double coupon;
    private double vat;
    private String cateId;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public Product(String productId, String productName, int quantity, double price, double coupon, double vat, String cateId) {
        this(productId, productName, quantity, price, coupon, vat);
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", coupon=" + coupon +
                ", vat=" + vat +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public Product() {
    }

    public Product(String productId, String productName, int quantity, double price, double coupon, double vat) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.vat = vat;
        this.coupon = coupon;
    }
}
