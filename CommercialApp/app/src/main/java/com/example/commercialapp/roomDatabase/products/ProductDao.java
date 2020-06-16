package com.example.commercialapp.roomDatabase.products;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product p);

    @Update
    void update(Product p);

    @Delete
    void delete(Product p);

    @Query("delete from products_table")
    void deleteAll();

    @Query("select * from products_table where (orderRowId =:orderRowId )")
    LiveData<List<Product>> getAllProductsForOrder(long orderRowId);

    @Query("select * from products_table")
    LiveData<List<Product>> getAllProducts();

    @Query("select * from products_table where (orderRowId = (select rowId from orders_table where status = 0))")
    LiveData<List<Product>> getAllProductsForOpenedOrder();

}
