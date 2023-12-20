package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private List<UserInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //隐藏状态栏
        setContentView(R.layout.activity_splash);
        MyApplication application = (MyApplication) getApplication();
        application.service = new Service(this);
        list = application.service.getUserInfo();

        new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    if(list == null || list.size() == 0){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userInfo", list.get(0));
                        startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                finish();
            }
        }.start();
    }
}