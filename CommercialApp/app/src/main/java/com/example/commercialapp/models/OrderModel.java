package com.example.commercialapp.models;

import com.example.commercialapp.roomDatabase.products.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class OrderModel {

    public static final String ID = "sifra";
    public static final String QUANTITY = "kolicina";
    public static final String BUYER = "kupac";
    public static final String DELIVERY_PLACE = "mestoIsporuke";
    public static final String NOTE = "napomena";
    public static final String HEADER = "zaglavlje";
    public static final String ITEMS = "stavke";
    public static final String DATE = "datum";

    private String buyer;
    private String deliveryPlace;
    private String note;
    private String date;
    private List<Product> productList;

    public OrderModel(String buyer, String deliveryPlace, String note, String date, List<Product> productList) {
        this.buyer = buyer;
        this.deliveryPlace = deliveryPlace;
        this.note = note;
        this.date = date;
        this.productList = productList;

    }

    public JSONObject toJSON() throws JSONException {

        JSONArray jsonItems = new JSONArray();

        for (Product product : productList) {
            JSONObject item = new JSONObject();
            item.put(ID, product.getB());
            item.put(QUANTITY, product.getQuantityAsString());
            jsonItems.put(item);
        }

        JSONObject result = new JSONObject();
        JSONObject header = new JSONObject();

        header.put(BUYER, buyer);
        header.put(DELIVERY_PLACE, deliveryPlace);
        header.put(NOTE, note);
        header.put(DATE, date);

        result.put(HEADER, header);
        result.put(ITEMS, jsonItems);

        return result;
    }
}
