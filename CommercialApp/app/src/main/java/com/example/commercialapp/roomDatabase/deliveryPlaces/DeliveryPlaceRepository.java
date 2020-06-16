package com.example.commercialapp.roomDatabase.deliveryPlaces;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.commercialapp.roomDatabase.CommercialDatabase;

import java.util.List;

public class DeliveryPlaceRepository {

    private DeliveryPlaceDao deliveryPlaceDao;
    private LiveData<List<DeliveryPlace>> allDeliveryPlaces;

    public DeliveryPlaceRepository(Application application) {
        CommercialDatabase database = CommercialDatabase.getInstance(application);
        deliveryPlaceDao = database.deliveryPlaceDao();
        allDeliveryPlaces = deliveryPlaceDao.getAllDeliveryPlaces();
    }

    public void insert(DeliveryPlace deliveryPlace) {
        new InsertDeliveryPlaceAsyncTask(deliveryPlaceDao).execute(deliveryPlace);
    }


    public LiveData<List<DeliveryPlace>> getAllDeliveryPlaces() {
        return allDeliveryPlaces;
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(deliveryPlaceDao).execute();
    }

    private static class InsertDeliveryPlaceAsyncTask extends AsyncTask<DeliveryPlace, Void, Void> {

        private DeliveryPlaceDao dao;

        public InsertDeliveryPlaceAsyncTask(DeliveryPlaceDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(DeliveryPlace... deliveryPlaces) {
            dao.insert(deliveryPlaces[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<DeliveryPlace, Void, Void> {

        private DeliveryPlaceDao dao;

        public DeleteAllAsyncTask(DeliveryPlaceDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DeliveryPlace... deliveryPlaces) {
            dao.deleteAll();
            return null;
        }
    }
}
