package com.example.commercialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.commercialapp.asyncResponses.AsyncResponse;
import com.example.commercialapp.asyncResponses.GetUserFromDbAsyncResponse;
import com.example.commercialapp.models.LoginUserResult;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlaceViewModel;
import com.example.commercialapp.roomDatabase.user.User;
import com.example.commercialapp.roomDatabase.user.UserViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements AsyncResponse {

    private UserViewModel userViewModel;
    private DeliveryPlaceViewModel deliveryPlaceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);

        TextView messageTextView = findViewById(R.id.text_view_message);
        messageTextView.setText("");

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        deliveryPlaceViewModel = ViewModelProviders.of(this).get(DeliveryPlaceViewModel.class);
    }

    public void onClickLogin(View view) {
        EditText usernameEditText = findViewById(R.id.edit_text_username);
        EditText passwordEditText = findViewById(R.id.edit_text_password);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        new GetUserFromApiAsyncTask(this, username, password).execute();

    }

    @Override
    public void processFinish(LoginUserResult result) {

        if (result != null) {
            User user = result.getUser();
            List<DeliveryPlace> places = result.getDeliveryPlaces();

            // save to db
            userViewModel.insert(user);
            for (DeliveryPlace place : places) {
                deliveryPlaceViewModel.insert(place);
            }

            // proceed to next activity
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_USER, user);
            startActivity(intent);
            finish();

        } else {
            // mark as error
            TextView messageTextView = findViewById(R.id.text_view_message);
            messageTextView.setText("Wrong username or password");
        }
    }


    private static class GetUserFromApiAsyncTask extends AsyncTask<Void, Void, LoginUserResult> {

        private AsyncResponse delegate;
        private String username;
        private String password;

        public GetUserFromApiAsyncTask(AsyncResponse delegate, String username, String password) {
            this.delegate = delegate;
            this.username = username;
            this.password = password;
        }

        @Override
        protected LoginUserResult doInBackground(Void... voids) {
            LoginUserResult result = JsonParser.getUserDataFromApi(username, password);
            return result;
        }

        @Override
        protected void onPostExecute(LoginUserResult res) {
            delegate.processFinish(res);
        }
    }
}
