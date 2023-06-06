package com.example.shoppingapp.Login;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    // Xử lý sự kiện click vào Fortgot password và Sign up
    private TextView LG_forgotPassword, LG_SignUpNow;
    private Button button;
    public ProgressBar progressbar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.login_email);
        passwordTextView = findViewById(R.id.login_password);
        button = findViewById(R.id.login_button);
        LG_forgotPassword = (TextView)findViewById(R.id.forgot_password);
        LG_SignUpNow = (TextView)findViewById(R.id.SignUpNow);

        // Set on Click Listener on Sign-in button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (email.isEmpty() || password.isEmpty() ) {
                            Toast.makeText(LoginActivity.this, "Please enter all required information.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                                "Login Successful",
                                                Toast.LENGTH_LONG)
                                        .show();
                                Intent intent
                                        = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Login failed!!"
                                                        + " Please try again later",
                                                Toast.LENGTH_LONG)
                                        .show();



                            }
                        }
                    }

                });
            }
        });

        LG_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
        LG_SignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}