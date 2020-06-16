package com.example.commercialapp.roomDatabase.deliveryPlaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeliveryPlaceDao {

    @Insert
    void insert(DeliveryPlace deliveryPlace);

    @Query("delete from deliveryPlace_table")
    void deleteAll();

    @Query("select * from deliveryPlace_table")
    LiveData<List<DeliveryPlace>> getAllDeliveryPlaces();

}
