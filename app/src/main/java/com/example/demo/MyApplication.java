package com.example.demo;

import android.app.Application;

public class MyApplication extends Application {
    public Service service;
    public NetworkService networkService = new NetworkService();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
