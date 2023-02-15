package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeLogoActivity extends AppCompatActivity {
    ImageView image;
    TextView logo;
    TextView slogan;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_logo);
        image = findViewById(R.id.imageView);

        logo = findViewById(R.id.textView);

        slogan = findViewById(R.id.textView2);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//Set animation to elements

        image.setAnimation(topAnim);

        logo.setAnimation(bottomAnim);

        slogan.setAnimation(bottomAnim);
    }
}