package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shoppingapp.R;

public class CheckOut extends AppCompatActivity {
    TextView btn_continue;
    ImageView backIcon;
    LinearLayout btn_add_card;
    RadioButton check_cash, check_onlinebanking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        backIcon = findViewById(R.id.backIcon);
        check_cash = findViewById(R.id.check_cashpayment);
        check_onlinebanking = findViewById(R.id.check_onlinebanking);
        btn_add_card = findViewById(R.id.btn_addcard);
        btn_continue = findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(CheckOut.this, BuyNow.class);
                startActivity(t);
            }
        });
        btn_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(CheckOut.this, NewCard.class);
                startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(CheckOut.this, BuyNow.class);
                startActivity(t);
            }
        });
    }
}