package com.example.shoppingapp.customerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;

public class DeliveryAddress extends AppCompatActivity {
    ImageView backIcon;
    TextView name, address, district, city, phone, btn_choose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        backIcon = findViewById(R.id.backIcon);
        name = findViewById(R.id.text_name);
        address = findViewById(R.id.text_address);
        district = findViewById(R.id.txt_district);
        city = findViewById(R.id.txt_city);
        phone =  findViewById(R.id.text_phone);
        btn_choose = findViewById(R.id.btn_choose_adress);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(DeliveryAddress.this, SaveAddress.class);
                //startActivity(t);
            }
        });

    }

}
