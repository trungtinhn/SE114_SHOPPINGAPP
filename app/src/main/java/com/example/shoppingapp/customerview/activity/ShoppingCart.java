package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shoppingapp.R;

public class ShoppingCart extends AppCompatActivity {
    ImageView backIcon;
    RadioButton checktotal;
    TextView Price, ButtonCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        backIcon = findViewById(R.id.backIcon);
        checktotal = findViewById(R.id.radio_btn_choose);
        Price = findViewById(R.id.txt_tonggia);
        ButtonCheckOut = findViewById(R.id.btn_checkout);


        ButtonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ShoppingCart.this, BuyNow.class);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(ShoppingCart.this, )
                //startActivity(t);
            }
        });
    }
}