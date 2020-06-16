package com.example.commercialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.commercialapp.asyncResponses.GetUserFromDbAsyncResponse;
import com.example.commercialapp.asyncResponses.UsersCountAsyncResponse;
import com.example.commercialapp.roomDatabase.user.User;
import com.example.commercialapp.roomDatabase.user.UserViewModel;

public class MainActivity extends AppCompatActivity implements GetUserFromDbAsyncResponse {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(this);
    }


    @Override
    public void processFinish(User output) {
        if(output == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_USER, output);
            startActivity(intent);
        }
        finish();
    }
}
