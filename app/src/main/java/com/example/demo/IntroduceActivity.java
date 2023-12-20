package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        ImageView imageView = findViewById(R.id.introduce_img);
        TextView titleText = findViewById(R.id.title);
        TextView contentText = findViewById(R.id.content);
        TextView commentText = findViewById(R.id.comment);

        Intent intent = getIntent();

        if (intent != null) {
            Attractions attractions = intent.getParcelableExtra("attractions");
            CheckInPoint checkInPoint = intent.getParcelableExtra("checkInPoint");

            if(attractions != null)
            {
                Glide.with(this).load(attractions.getPictureUrl()).into(imageView);
                titleText.setText(attractions.getTitle());
                contentText.setText(attractions.getContent());
                commentText.setText(attractions.getComment());
            }

            if(checkInPoint != null)
            {
                Glide.with(this).load(checkInPoint.getPictureUrl()).into(imageView);
                titleText.setText(checkInPoint.getTitle());
                contentText.setText(checkInPoint.getContent());
                commentText.setText(checkInPoint.getAddress());
            }
        }
    }
}
