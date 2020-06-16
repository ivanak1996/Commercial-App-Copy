package com.example.commercialapp.roomDatabase.user;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("delete from user_table")
    void deleteAll();

    @Query("select count(*) from user_table")
    int usersCount();

    @Query("select * from user_table")
    LiveData<List<User>> getAllUsers();

    @Query("select * from user_table limit 1")
    User getUser();
}
