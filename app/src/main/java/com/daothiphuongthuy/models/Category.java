package com.daothiphuongthuy.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String catId;
    private String catName;
    private String catDes;

    @Override
    public String toString() {
        return "Category{" +
                "catId='" + catId + '\'' +
                ", catName='" + catName + '\'' +
                ", catDes='" + catDes + '\'' +
                '}';
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDes() {
        return catDes;
    }

    public void setCatDes(String catDes) {
        this.catDes = catDes;
    }

    public Category() {
    }

    public Category(String catId, String catName, String catDes) {
        this.catId = catId;
        this.catName = catName;
        this.catDes = catDes;
    }
}
