package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btn1 = findViewById(R.id.page_one);
        ImageView btn2 = findViewById(R.id.page_two);
        ImageView btn3 = findViewById(R.id.page_three);
        ImageView btn4 = findViewById(R.id.page_four);
        ImageButton btn5 = findViewById(R.id.page_add);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        MyApplication application = (MyApplication) getApplication();
        application.service = new Service(this);

        Intent intent = getIntent();
        if(intent != null) {
            UserInfo userInfo = intent.getParcelableExtra("userInfo");

            if (userInfo != null) {
                application.service.insertUserInfo(userInfo);
                viewModel.data.postValue(userInfo);
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(savedInstanceState == null)
        {
            btn1.setSelected(true);
            fragmentTransaction.add(R.id.container,FragmentOne.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        btn1.setOnClickListener(v -> {
            btn1.setSelected(true);
            btn2.setSelected(false);
            btn3.setSelected(false);
            btn4.setSelected(false);
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.container,FragmentOne.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        });

        btn2.setOnClickListener(v -> {
            btn1.setSelected(false);
            btn2.setSelected(true);
            btn3.setSelected(false);
            btn4.setSelected(false);
            FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
            fragmentTransaction2.replace(R.id.container,FragmentTwo.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        });

        btn3.setOnClickListener(v -> {
            btn1.setSelected(false);
            btn2.setSelected(false);
            btn3.setSelected(true);
            btn4.setSelected(false);
            FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
            fragmentTransaction3.replace(R.id.container,FragmentThree.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        });

        btn4.setOnClickListener(v -> {
            btn1.setSelected(false);
            btn2.setSelected(false);
            btn3.setSelected(false);
            btn4.setSelected(true);
            FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
            fragmentTransaction4.replace(R.id.container,FragmentFour.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        });

        btn5.setOnClickListener(v -> startActivity(new Intent(this, AddActivity.class)));
    }
}