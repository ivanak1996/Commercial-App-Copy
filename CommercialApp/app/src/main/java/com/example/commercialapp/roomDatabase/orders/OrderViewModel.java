package com.example.commercialapp.roomDatabase.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.commercialapp.asyncResponses.GetOpenedOrderAsyncResponse;
import com.example.commercialapp.asyncResponses.InsertOrderAsyncResponse;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
    }

    public void insert(Order order) {
        orderRepository.insert(order);
    }

    public void insert(Order order, InsertOrderAsyncResponse response) {
        orderRepository.insert(order, response);
    }

    public void update(Order order) {
        orderRepository.update(order);
    }

    public void getOpenedOrder(GetOpenedOrderAsyncResponse response) {
        orderRepository.getOpenedOrder(response);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
