package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        application = (MyApplication) getApplication();

        EditText userName = findViewById(R.id.rg_username);
        EditText passWord = findViewById(R.id.rg_password);
        Button btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(v -> application.networkService.postUserInfo(new UserInfo(
               -1 , null, String.valueOf(userName.getText()), String.valueOf(passWord.getText()),1
        )));
    }
}