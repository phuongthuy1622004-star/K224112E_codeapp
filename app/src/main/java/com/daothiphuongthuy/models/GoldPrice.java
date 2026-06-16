package com.daothiphuongthuy.models;

import java.io.Serializable;

public class GoldPrice implements Serializable {
    private String label;
    private String date;
    private long buy;
    private long sell;

    public GoldPrice() {}

    public GoldPrice(String label, String date, long buy, long sell) {
        this.label = label;
        this.date = date;
        this.buy = buy;
        this.sell = sell;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getBuy() {
        return buy;
    }

    public void setBuy(long buy) {
        this.buy = buy;
    }

    public long getSell() {
        return sell;
    }

    public void setSell(long sell) {
        this.sell = sell;
    }
}
