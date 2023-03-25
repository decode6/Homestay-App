package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        RelativeLayout tourist = (RelativeLayout) findViewById(R.id.tourist);
        RelativeLayout owner = (RelativeLayout) findViewById(R.id.owner);

//        RelativeLayout admin = (RelativeLayout) findViewById(R.id.admin);


        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, TouristLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}