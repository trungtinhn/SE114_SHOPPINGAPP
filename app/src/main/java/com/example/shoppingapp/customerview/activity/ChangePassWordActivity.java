package com.example.shoppingapp.customerview.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shoppingapp.Login.Capcha;
import com.example.shoppingapp.Login.ForgotPassword;
import com.example.shoppingapp.Login.LoginActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.fragment.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassWordActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private Button mVerifyButton;
    private ImageButton back_to_Home;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);

        // Khởi tạo FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Lấy đối tượng EditText và Button từ layout
        mEmailEditText = findViewById(R.id.email_edittext_ChangePass);
        mVerifyButton = findViewById(R.id.verify_button_ChangePass);
        back_to_Home = findViewById(R.id.btnBacktoAccount);
        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassWordActivity.this, BottomNavigationCustomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ChangePassWordActivity.this,
                            "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gửi email xác thực từ Firebase đến email của người dùng
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showSuccessDialog();
                                } else {
                                    Toast.makeText(ChangePassWordActivity.this,
                                            "Error sending reset password email",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            private void showSuccessDialog() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassWordActivity.this);
                                builder.setTitle("Success");
                                builder.setMessage("Reset password email sent. Please check your Email and Reset your password");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Chuyển hướng đến LoginActivity
                                        Intent intent = new Intent(ChangePassWordActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });

            }
        });

    }
}