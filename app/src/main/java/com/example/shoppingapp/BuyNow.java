package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyNow extends AppCompatActivity {
    ImageView backbtn;
    TextView Subtotal, Delivery, CheckOut, Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        backbtn = findViewById(R.id.backIcon);
        Subtotal = findViewById(R.id.subtotal_text);
        Delivery = findViewById(R.id.delivery_text);
        CheckOut = findViewById(R.id.btn_checkout);

        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(BuyNow.this, );

            }
        });
    }
}