package com.example.shoppingapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.example.shoppingapp.customerview.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    // Xử lý sự kiện click vào Fortgot password và Sign up
    private TextView LG_forgotPassword, LG_SignUpNow;
    private Button button;
    private ImageButton btn_showpassword;
    public ProgressBar progressbar;

    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // initialising all views through id defined above
        emailTextView = findViewById(R.id.login_email);
        passwordTextView = findViewById(R.id.login_password);
        button = findViewById(R.id.login_button);
        LG_forgotPassword = (TextView)findViewById(R.id.forgot_password);
        btn_showpassword = findViewById(R.id.showPassword);
        LG_SignUpNow = (TextView)findViewById(R.id.SignUpNow);

        // Set on Click Listener on Sign-in button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter all required information.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Xác thực thành công
                                String emailhandle = mAuth.getCurrentUser().getEmail();
                                String email_change = Base64.getEncoder().encodeToString(emailhandle.getBytes());
                                DatabaseReference userRef = firebaseDatabase.getReference("Users").child(email_change);
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String userType = dataSnapshot.child("LoaiND").getValue(String.class);
                                        if (userType != null) {
                                            switch (userType) {
                                                case "customer":
                                                    // Người dùng là khách hàng
                                                    Toast.makeText(getApplicationContext(), "Login as customer",
                                                            Toast.LENGTH_SHORT).show();
                                                    // Chuyển người dùng đến màn hình khách hàng
                                                    Intent customerIntent = new Intent(LoginActivity.this, HomeFragment.class);
                                                    startActivity(customerIntent);
                                                    finish();
                                                    break;
                                                case "Staff":
                                                    // Người dùng là nhân viên
                                                    Toast.makeText(getApplicationContext(), "Login as staff",
                                                            Toast.LENGTH_SHORT).show();
                                                    // Chuyển người dùng đến màn hình nhân viên
                                                    Intent staffIntent = new Intent(LoginActivity.this, home_page.class);
                                                    startActivity(staffIntent);
                                                    finish();
                                                    break;
                                                case "Admin":
                                                    // Người dùng là admin
                                                    Toast.makeText(getApplicationContext(), "Login as admin",
                                                            Toast.LENGTH_SHORT).show();
                                                    // Chuyển người dùng đến màn hình admin
                                                    Intent adminIntent = new Intent(LoginActivity.this, home_page.class);
                                                    startActivity(adminIntent);
                                                    finish();
                                                    break;
                                                default:
                                                    // Giá trị không hợp lệ
                                                    Toast.makeText(getApplicationContext(), "Invalid user type",
                                                            Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Xử lý lỗi nếu có
                                    }
                                });
                            } else if (!task.isSuccessful()) {
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Login failed!!"
                                                        + " Please try again later",
                                                Toast.LENGTH_LONG)
                                        .show();

                            }
                        }

                    });
                }
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