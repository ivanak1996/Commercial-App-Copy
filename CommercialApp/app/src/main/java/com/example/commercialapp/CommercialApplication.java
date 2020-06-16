package com.example.commercialapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class CommercialApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
