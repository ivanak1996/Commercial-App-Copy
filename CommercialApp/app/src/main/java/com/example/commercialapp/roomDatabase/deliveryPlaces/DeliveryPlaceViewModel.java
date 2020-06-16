package com.example.commercialapp.roomDatabase.deliveryPlaces;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DeliveryPlaceViewModel extends AndroidViewModel {

    private DeliveryPlaceRepository deliveryPlaceRepository;
    private LiveData<List<DeliveryPlace>> allDeliveryPlaces;

    public DeliveryPlaceViewModel(@NonNull Application application) {
        super(application);
        deliveryPlaceRepository = new DeliveryPlaceRepository(application);
        allDeliveryPlaces = deliveryPlaceRepository.getAllDeliveryPlaces();
    }

    public void insert(DeliveryPlace dp) {
        deliveryPlaceRepository.insert(dp);
    }

    public LiveData<List<DeliveryPlace>> getAllDeliveryPlaces() {
        return allDeliveryPlaces;
    }

    public void deleteAll() {
        deliveryPlaceRepository.deleteAll();
    }
}
