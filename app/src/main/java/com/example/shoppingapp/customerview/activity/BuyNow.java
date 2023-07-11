package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;

public class BuyNow extends AppCompatActivity {
    ImageView backbtn;
    //TextAcount
    TextView Subtotal, Delivery, CheckOut, Total, ClickGiamGia, ClickAddress, ClickPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        backbtn = findViewById(R.id.backIcon);
        Subtotal = findViewById(R.id.subtotal_text);
        Delivery = findViewById(R.id.delivery_text);
        CheckOut = findViewById(R.id.btn_checkout);
        ClickGiamGia = findViewById(R.id.click_giamgia);
        ClickAddress = findViewById(R.id.click_address);
        ClickPayment = findViewById(R.id.click_payment);
        ClickPayment = findViewById(R.id.click_payment);

        ClickPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, CheckOut.class);
                startActivity(t);
            }
        });
        ClickAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, SaveAddress.class);
                startActivity(t);
            }
        });
        ClickGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(BuyNow.this, )
                //startActivity(t);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent(BuyNow.this, ShoppingCart.class);
                startActivity(t);
            }
        });
        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, DoneActivity.class);
                startActivity(t);

            }
        });
    }

}