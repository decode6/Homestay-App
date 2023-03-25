package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class OwnerDashboardActivity extends AppCompatActivity {
    TextView welcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
//        CardView profile = findViewById(R.id.idprofile);
        CardView addproperty = findViewById(R.id.idaddproperty);

        CardView view = findViewById(R.id.idviewproperty);
        CardView logout = findViewById(R.id.idlogout);
        welcomeTextView=findViewById(R.id.welcomeviewname);
        String welcomeMessage="Welcome "+ ParseUser.getCurrentUser().getUsername();
        welcomeTextView.setText(welcomeMessage);
//
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OwnerDashboardActivity.this, ProfileActivity.class);
//                intent.putExtra("username", ParseUser.getCurrentUser().getUsername());
//                startActivity(intent);
//            }
//        });
        addproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboardActivity.this, AddPropertyActivity.class);
                startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboardActivity.this, PropertyViewActivity.class);
                startActivity(intent);
            }
        });
//        help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CurrentActivity.this, OtherActivity.class);
//                startActivity(intent);
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}