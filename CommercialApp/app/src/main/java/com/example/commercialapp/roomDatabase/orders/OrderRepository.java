package com.example.commercialapp.roomDatabase.orders;

import android.app.Application;
import android.os.AsyncTask;

import com.example.commercialapp.asyncResponses.GetOpenedOrderAsyncResponse;
import com.example.commercialapp.asyncResponses.InsertOrderAsyncResponse;
import com.example.commercialapp.roomDatabase.CommercialDatabase;

public class OrderRepository {

    private OrderDao orderDao;

    public OrderRepository(Application application) {
        CommercialDatabase database = CommercialDatabase.getInstance(application);
        orderDao = database.orderDao();
    }

    public void insert(Order order, InsertOrderAsyncResponse response) {
        new InsertOrderAsyncTask(orderDao, response).execute(order);
    }

    public void insert(final Order order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderDao.insert(order);
            }
        }).start();
    }

    public void update(final Order order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderDao.update(order);
            }
        }).start();
    }

    public void getOpenedOrder(GetOpenedOrderAsyncResponse response) {
        new OpenedOrderAsyncTask(orderDao, response).execute();
    }

    public void deleteAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderDao.deleteAll();
            }
        }).start();
    }

    private static class InsertOrderAsyncTask extends AsyncTask<Order, Void, Long> {

        private OrderDao orderDao;
        private InsertOrderAsyncResponse response;

        public InsertOrderAsyncTask(OrderDao orderDao, InsertOrderAsyncResponse response) {
            this.orderDao = orderDao;
            this.response = response;
        }

        @Override
        protected Long doInBackground(Order... orders) {
            return orderDao.insert(orders[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            response.insertOrderFinish(id);
        }
    }

    private static class OpenedOrderAsyncTask extends AsyncTask<Void, Void, Order> {

        private OrderDao orderDao;
        private GetOpenedOrderAsyncResponse response;

        public OpenedOrderAsyncTask(OrderDao orderDao, GetOpenedOrderAsyncResponse response) {
            this.orderDao = orderDao;
            this.response = response;
        }

        @Override
        protected Order doInBackground(Void... voids) {
            return orderDao.getOpenedOrder();
        }

        @Override
        protected void onPostExecute(Order order) {
            response.getOpenedOrderFinish(order);
        }
    }

}
