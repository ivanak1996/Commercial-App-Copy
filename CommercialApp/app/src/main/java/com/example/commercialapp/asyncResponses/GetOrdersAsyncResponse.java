package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.models.orderHistoryModels.OrderHistoryModel;

import java.util.List;

public interface GetOrdersAsyncResponse {
    void finish(List<OrderHistoryModel> orders);
}
