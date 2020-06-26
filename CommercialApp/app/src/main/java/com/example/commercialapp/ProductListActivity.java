package com.example.commercialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.*;
import androidx.navigation.ui.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commercialapp.asyncResponses.GetOpenedOrderAsyncResponse;
import com.example.commercialapp.asyncResponses.PlaceAnOrderAsyncResponse;
import com.example.commercialapp.fragments.DatePickerFragment;
import com.example.commercialapp.models.OrderModel;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlaceViewModel;
import com.example.commercialapp.roomDatabase.orders.Order;
import com.example.commercialapp.roomDatabase.orders.OrderViewModel;
import com.example.commercialapp.roomDatabase.products.Product;
import com.example.commercialapp.roomDatabase.products.ProductViewModel;
import com.example.commercialapp.roomDatabase.user.User;
import com.example.commercialapp.roomDatabase.user.UserViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener, PlaceAnOrderAsyncResponse {

    public static final String EXTRA_USER = "EXTRA_USER";

    private UserViewModel userViewModel;
    private DeliveryPlaceViewModel deliveryPlaceViewModel;
    private ProductViewModel productViewModel;
    private OrderViewModel orderViewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private User user;

    private List<Product> products;

    private long previousDrawerItemClicked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        deliveryPlaceViewModel = ViewModelProviders.of(this).get(DeliveryPlaceViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        productViewModel.getAllProductsInOpenedOrder().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                ProductListActivity.this.products = products;
            }
        });

        Intent caller = getIntent();
        this.user = (User) caller.getSerializableExtra(EXTRA_USER);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();

    }

    public User getUser() {
        return user;
    }

    private void init() {
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.productListScreenFragment);
        topLevelDestinations.add(R.id.anotherFragment);
        topLevelDestinations.add(R.id.orderHistoryFragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void logout() {
        deliveryPlaceViewModel.deleteAll();
        productViewModel.deleteAll();
        orderViewModel.deleteAll();
        userViewModel.deleteAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != previousDrawerItemClicked) {
            switch (menuItem.getItemId()) {
                case R.id.nav_logout: {
                    logout();
                    break;
                }
                case R.id.nav_products: {
                    // nav options to clear backstack
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.main, false)
                            .build();
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(R.id.productListScreenFragment, null, navOptions);
                    break;
                }
                case R.id.nav_orders: {
                    // TODO: orders history shall be here
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(R.id.orderHistoryFragment);
                    break;
                }
            }
        }
        previousDrawerItemClicked = menuItem.getItemId();
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else {
                    return false;
                }
            case R.id.action_chart: {
                // go to chart
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.anotherFragment);
                break;
            }
            case R.id.action_next: {
                /*Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.orderExtraFragment);*/
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), appBarConfiguration);
    }

    public void placeAnOrder(View view) {
        Spinner deliveryPlaceSpinner = findViewById(R.id.spinner_delivery_place);
        TextView notesTextView = findViewById(R.id.notes_textview);
        TextView deliveryPlaceTextView = findViewById(R.id.text_view_delivery_place);
        String ss = "";
        if (deliveryPlaceSpinner.getSelectedItem() != null) {
            ss = "" + deliveryPlaceSpinner.getSelectedItem();
        } else {
            if (!deliveryPlaceTextView.getText().toString().trim().equals("")) {
                ss = deliveryPlaceTextView.getText().toString();
            } else {
                ss = user.getAcName2();
            }
        }
        OrderModel orderModel = new OrderModel(user.getAcName2(), ss, notesTextView.getText().toString(), products);
        new PlaceAnOrderAsyncTask(user.getEmail(), user.getPassword(), orderModel, this).execute();
    }

    @Override
    public void processFinish(boolean success) {
        if (success) {
            productViewModel.deleteAll();
            orderViewModel.deleteAll();
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.main, false)
                    .build();
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.productListScreenFragment, null, navOptions);
            Toast.makeText(this, R.string.sent_order, Toast.LENGTH_SHORT).show();
            orderViewModel.insert(new Order(Order.STATUS_OPEN, new Date(System.currentTimeMillis())));
        } else {
            Toast.makeText(this, R.string.error_order, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSummonDatePicker(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        TextView textViewDate = findViewById(R.id.text_view_delivery_date);
        textViewDate.setText(currDate);

    }

    private static class PlaceAnOrderAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private PlaceAnOrderAsyncResponse delegate;
        private String username;
        private String password;
        private OrderModel orderModel;

        public PlaceAnOrderAsyncTask(String username, String password, OrderModel orderModel, PlaceAnOrderAsyncResponse delegate) {
            this.delegate = delegate;
            this.username = username;
            this.password = password;
            this.orderModel = orderModel;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return JsonParser.sendOrderData(username, password, orderModel);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            delegate.processFinish(aBoolean);
        }
    }
}
