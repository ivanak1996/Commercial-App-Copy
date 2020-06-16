package com.example.commercialapp.asyncResponses;

import com.example.commercialapp.models.LoginUserResult;

public interface AsyncResponse {
    void processFinish(LoginUserResult output);
}
