package com.example.commercialapp.roomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlaceDao;
import com.example.commercialapp.roomDatabase.orders.Order;
import com.example.commercialapp.roomDatabase.orders.OrderDao;
import com.example.commercialapp.roomDatabase.products.Product;
import com.example.commercialapp.roomDatabase.products.ProductDao;
import com.example.commercialapp.roomDatabase.user.User;
import com.example.commercialapp.roomDatabase.user.UserDao;


@Database(entities = {User.class, DeliveryPlace.class, Order.class, Product.class}, version = 9, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CommercialDatabase extends RoomDatabase {

    private static CommercialDatabase instance;

    public abstract UserDao userDao();

    public abstract DeliveryPlaceDao deliveryPlaceDao();

    public abstract OrderDao orderDao();

    public abstract ProductDao productDao();

    public static synchronized CommercialDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CommercialDatabase.class, "commercial_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;

        public PopulateDbAsyncTask(CommercialDatabase db) {
            userDao = db.userDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            //User initialUser = new User("adm", "123", "", "http://89.216.122.162:8080/tkomserver/jsonData/api");
            //userDao.insert(initialUser);
            return null;
        }
    }
}
