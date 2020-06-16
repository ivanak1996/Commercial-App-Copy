package com.example.commercialapp.roomDatabase.user;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.commercialapp.asyncResponses.GetUserFromDbAsyncResponse;
import com.example.commercialapp.asyncResponses.UsersCountAsyncResponse;
import com.example.commercialapp.roomDatabase.CommercialDatabase;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        CommercialDatabase database = CommercialDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void usersCount(UsersCountAsyncResponse response) {
        new UsersCountAsyncTask(userDao, response).execute();
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(userDao).execute();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void getUser(GetUserFromDbAsyncResponse response) {
        new GetUserAsyncTask(userDao, response).execute();
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;

        public DeleteAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }

    private static class UsersCountAsyncTask extends AsyncTask<Void, Void, Integer> {

        private UserDao userDao;
        private UsersCountAsyncResponse response;

        public UsersCountAsyncTask(UserDao userDao, UsersCountAsyncResponse response) {
            this.userDao = userDao;
            this.response = response;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return userDao.usersCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            response.processFinish(integer.intValue());
        }
    }

    private static class GetUserAsyncTask extends AsyncTask<Void, Void, User> {

        private UserDao userDao;
        private GetUserFromDbAsyncResponse response;

        public GetUserAsyncTask(UserDao userDao, GetUserFromDbAsyncResponse response) {
            this.userDao = userDao;
            this.response = response;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getUser();
        }

        @Override
        protected void onPostExecute(User user) {
            response.processFinish(user);
        }
    }
}
