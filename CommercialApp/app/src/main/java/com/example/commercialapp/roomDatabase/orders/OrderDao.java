package com.example.commercialapp.roomDatabase.orders;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface OrderDao {

    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Query("select * from orders_table where status = 0")
    Order getOpenedOrder();

    @Query("delete from orders_table")
    void deleteAll();
}
