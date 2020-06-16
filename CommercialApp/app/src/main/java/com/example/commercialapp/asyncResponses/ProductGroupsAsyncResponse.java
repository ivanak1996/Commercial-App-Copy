package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.models.ProductGroupModel;

import java.util.List;

public interface ProductGroupsAsyncResponse {
    void productGroupsProcessFinish(List<ProductGroupModel> productGroups);
}
