package com.example.commercialapp.models;

import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.user.User;

import java.util.List;

public class LoginUserResult {

    private User user;
    private List<DeliveryPlace> deliveryPlaces;

    public LoginUserResult(User user, List<DeliveryPlace> deliveryPlaces) {
        this.user = user;
        this.deliveryPlaces = deliveryPlaces;
    }

    public User getUser() {
        return user;
    }

    public List<DeliveryPlace> getDeliveryPlaces() {
        return deliveryPlaces;
    }

}
