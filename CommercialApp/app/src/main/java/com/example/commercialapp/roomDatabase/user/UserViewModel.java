package com.example.commercialapp.roomDatabase.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.commercialapp.asyncResponses.GetUserFromDbAsyncResponse;
import com.example.commercialapp.asyncResponses.UsersCountAsyncResponse;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void usersCount(UsersCountAsyncResponse response) {
        userRepository.usersCount(response);
    }

    public void getUser(GetUserFromDbAsyncResponse response) {
        userRepository.getUser(response);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

}
