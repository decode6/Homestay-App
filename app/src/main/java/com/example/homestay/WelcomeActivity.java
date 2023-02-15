package com.example.homestay;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

//public class WelcomeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
//    }
//}

public class WelcomeActivity<UserActivity> extends AppCompatActivity {

//    Button logoutButton;
    TextView welcomeTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
//        logoutButton=findViewById(R.id.logoutButton);
        welcomeTextView=findViewById(R.id.welcomeTextView);
        String welcomeMessage="Welcome "+ ParseUser.getCurrentUser().getUsername()+"Email : " + ParseUser.getCurrentUser().getEmail();
        welcomeTextView.setText(welcomeMessage);
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseUser.logOut();
//                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout_menu:
                ParseUser.logOut();
                final Intent intent1 = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_item_menu:
                //ParseUser.logOut();
                final Intent intent2 = new Intent(WelcomeActivity.this,AddPropertyActivity.class);
                startActivity(intent2);

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                final EditText noteEditText = new EditText(this);
//                builder.setTitle("Write a Note")
//                        .setView(noteEditText)
//                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ParseObject note = new ParseObject("Note");
//                                note.put("username",ParseUser.getCurrentUser().getUsername());
//                                note.put("note",noteEditText.getText().toString());
//                                note.saveInBackground(new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException e) {
//                                        if (e==null){
//                                            Toast.makeText(WelcomeActivity.this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
//                                            refreshActivity();
//                                        }else {
//                                            Toast.makeText(WelcomeActivity.this, "Oops - Try again later.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }j
//
//                                    private void refreshActivity() {
//                                    }
//                                });
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        })
//                        .show();
                break;
          default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
