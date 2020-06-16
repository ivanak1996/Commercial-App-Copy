package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.roomDatabase.user.User;

public interface GetUserFromDbAsyncResponse {
    void processFinish(User output);
}
