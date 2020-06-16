package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.models.orderHistoryModels.ProductHistoryModel;

import java.util.List;

public interface GetOrderItemsAsyncResponse {
    void finish(List<ProductHistoryModel> products);
}
