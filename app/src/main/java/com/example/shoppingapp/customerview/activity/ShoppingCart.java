package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.shoppingcart.ShoppingAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    RecyclerView recyclerViewdata;
    ShoppingAdapter shoppingAdapter;
    List<ShoppingCart> data;

    ImageView backIcon;
    RadioButton checktotal;
    TextView Price, ButtonCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerViewdata = findViewById(R.id.RecyclerData);
        backIcon = findViewById(R.id.backIcon);
        checktotal = findViewById(R.id.radio_btn_choose);
        Price = findViewById(R.id.txt_tonggia);
        ButtonCheckOut = findViewById(R.id.btn_checkout);

        data = new ArrayList<>();
        //Add du lieu
        shoppingAdapter = new ShoppingAdapter(this.getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewdata.setLayoutManager(linearLayoutManager);
        recyclerViewdata.setAdapter(shoppingAdapter);

        ButtonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent(ShoppingCart.this, BuyNow.class);
                startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ShoppingCart.this, BottomNavigationCustomActivity.class);
                startActivity(t);
            }
        });
    }
}