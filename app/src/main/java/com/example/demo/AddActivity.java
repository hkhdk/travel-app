package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnLeft = findViewById(R.id.left);
        Button btnRight = findViewById(R.id.right);

        btnLeft.setOnClickListener(v -> startActivity(new Intent(this, ReleaseActivity.class)));

        btnRight.setOnClickListener(v -> startActivity(new Intent(this, WebViewActivity.class)));
    }
}