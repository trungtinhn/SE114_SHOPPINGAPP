package com.example.shoppingapp.StaffView.MyOrder.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Activity.activity_MyOrder;
import com.example.shoppingapp.StaffView.activity.activity_List_Product_And_Item;

public class activity_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home_page);
        Button btn_view_shop= findViewById(R.id.btn_view_shop);
        btn_view_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_List_Product_And_Item.class);
                startActivity(intent);
            }
        });

        Button btnMyOrder = findViewById(R.id.btn_my_order);
        btnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_MyOrder.class);
                startActivity(intent);
            }
        });
    }
} 