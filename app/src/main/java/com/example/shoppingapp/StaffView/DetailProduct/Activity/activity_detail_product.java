package com.example.shoppingapp.StaffView.DetailProduct.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.BottomNavigationStaffActivity;

public class activity_detail_product extends AppCompatActivity {

    private ImageView backIcon;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        backIcon = findViewById(R.id.backIcon);


        setOnClickBackICon();
    }
    private void setOnClickBackICon() {

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_detail_product.this, BottomNavigationStaffActivity.class);
                startActivity(intent);
            }
        });
    }
}