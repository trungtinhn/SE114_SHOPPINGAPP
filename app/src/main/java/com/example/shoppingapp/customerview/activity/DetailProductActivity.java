package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;

public class DetailProductActivity extends AppCompatActivity {

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
                Intent intent = new Intent(DetailProductActivity.this, BottomNavigationCustomActivity.class);
                startActivity(intent);
            }
        });
    }
}