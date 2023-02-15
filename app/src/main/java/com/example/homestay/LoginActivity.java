
package com.example.homestay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homestay.MainActivity;
import com.example.homestay.R;
import com.example.homestay.WelcomeActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private TextView forgotPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        emailEditText=findViewById(R.id.emailEditTextLogin);
        passwordEditText=findViewById(R.id.passwordEditTextLogin);
        loginButton=findViewById(R.id.buttonLogin);
        signUpTextView=findViewById(R.id.textViewLogin2);
        forgotPassword=findViewById(R.id.forgotPassword);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordActivity();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText().toString()!=null && passwordEditText.getText().toString()!=null){
                    ParseUser.logInInBackground(emailEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e==null){
                                showAlert("Login Successful","Your email is verified successfully",false);
                            }else {
                                showAlert("Login failed",e.getMessage(),true);
                            }
                        }
                    });
                }
            }
        });

    }
    private void startSignUpActivity(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    private void ForgotPasswordActivity(){
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    private void startWelcomeActivity() {
        Intent intent = new Intent(LoginActivity.this, OwnerDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlert(String title, String message, Boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!error){
                            startWelcomeActivity();
                        }
                    }
                })
                .show();
    }
}
