package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shoppingapp.R;

public class SaveAddress extends AppCompatActivity {
    ImageView backIcon ;
    LinearLayout buttonadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_address);
        backIcon = findViewById(R.id.backIcon);
        buttonadd = findViewById(R.id.btn_add_address);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(SaveAddress.this, BuyNow.DeliveryAddress.class);
                //startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(SaveAddress.this, BuyNow.class);
                //startActivity(t);
            }
        });
    }
}