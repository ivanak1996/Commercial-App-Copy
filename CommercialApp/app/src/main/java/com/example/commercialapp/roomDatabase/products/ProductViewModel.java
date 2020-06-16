package com.example.commercialapp.roomDatabase.products;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.commercialapp.roomDatabase.orders.Order;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> allProductsInOpenedOrder;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProducts = productRepository.getAllProducts();
        allProductsInOpenedOrder = productRepository.getAllProductsInOpenedOrder();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product product, long orderRowId) {
        if (product.getQuantity() == 0) {
            productRepository.delete(product);
        } else {
            product.setOrderRowId(orderRowId);
            productRepository.insert(product);
        }
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public LiveData<List<Product>> getAllProductsForOrder(@NonNull Order order) {
        return productRepository.getAllProductsForOrder(order.getRowId());
    }

    public LiveData<List<Product>> getAllProductsInOpenedOrder() {
        return allProductsInOpenedOrder;
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}
