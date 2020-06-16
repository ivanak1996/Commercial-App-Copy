package com.example.commercialapp.models;

import androidx.annotation.NonNull;

public class ProductGroupModel {

    private String a;
    private String b;

    public ProductGroupModel(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    @NonNull
    @Override
    public String toString() {
        return b;
    }
}
