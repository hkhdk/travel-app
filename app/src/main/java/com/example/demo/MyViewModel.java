package com.example.demo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public MutableLiveData<UserInfo> data = new MutableLiveData<>();
}
