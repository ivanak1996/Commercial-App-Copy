package com.example.commercialapp.models.orderHistoryModels;

public class OrderHistoryModel {
    private String buyer;
    private String date;
    private String price;
    private String key;

    public OrderHistoryModel(String buyer, String date, String price, String key) {
        this.buyer = buyer;
        this.date = date;
        this.price = price;
        this.key = key;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getKey() {
        return key;
    }
}
