package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application = (MyApplication) getApplication();
        Button btnLogin = findViewById(R.id.lg_login);
        EditText userName = findViewById(R.id.lg_username);
        EditText passWord = findViewById(R.id.lg_password);
        TextView register = findViewById(R.id.lg_register);

        register.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            if(String.valueOf(userName.getText()).equals("") || String.valueOf(passWord.getText()).equals((""))){
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            }else{
                application.networkService.userLogin(String.valueOf(userName.getText()), String.valueOf(passWord.getText()),
                        new NetworkService.UserLoginSuccessOrFail() {
                            @Override
                            public void userLoginSuccess(UserInfo userInfo) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userInfo", userInfo);
                                startActivity(intent);
                            }
                            @Override
                            public void userLoginFail() {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误,请重试", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}