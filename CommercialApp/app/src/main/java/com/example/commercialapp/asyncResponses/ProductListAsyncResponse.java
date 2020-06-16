package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.roomDatabase.products.Product;

import java.util.List;

public interface ProductListAsyncResponse {
    void processFinish(List<Product> models, boolean hasError);
}
