package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.roomDatabase.orders.Order;

public interface GetOpenedOrderAsyncResponse {
    void getOpenedOrderFinish(Order order);
}
