package com.example.shoppingapp.StaffView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;

public class activity_click_staff_check extends AppCompatActivity {

    Button btnOpen;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_staff_click_check);

        btnOpen = findViewById(R.id.button15);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_click_staff_check.this, activity_List_Product_And_Item.class);
                startActivity(intent);
            }
        });
    }
}