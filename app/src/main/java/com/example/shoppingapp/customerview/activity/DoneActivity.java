package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;

public class DoneActivity extends AppCompatActivity {
    TextView checkstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        checkstatus = findViewById(R.id.btn_checkstatus);

        checkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(DoneActivity.this, BottomNavigationCustomActivity.class);
                startActivity(t);
            }
        });
    }
}