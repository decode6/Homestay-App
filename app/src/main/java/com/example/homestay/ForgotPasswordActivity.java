package com.example.homestay;

        import android.annotation.SuppressLint;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.example.homestay.R;
        import com.parse.Parse;
        import com.parse.ParseException;
        import com.parse.ParseUser;
        import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText mEmailField;
    private Button resetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Parse SDK
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("23TSxUoSLqQ5BxbXpXCr6VMdTYO0ti5evyY035rL")
                .clientKey("8D3Lj9c8EP69iJHNrzzldlLEOkw9UydalammYs8S")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        // Set up email input field and reset password button
        mEmailField = findViewById(R.id.emailEditText);
        resetButton = findViewById(R.id.resetPasswordButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString().trim();
                resetPassword(email);
            }
        });
    }

    private void resetPassword(String email) {
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Password reset email sent successfully
                    Toast.makeText(getApplicationContext(), "Password reset email sent!", Toast.LENGTH_SHORT).show();
                } else {
                    // Error occurred during password reset process
                    Toast.makeText(getApplicationContext(), "Error resetting password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
