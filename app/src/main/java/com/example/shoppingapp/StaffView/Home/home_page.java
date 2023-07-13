package com.example.shoppingapp.StaffView.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Activity.activity_MyOrder;
import com.example.shoppingapp.StaffView.MyProduct.Activity.activity_MyProduct;
import com.example.shoppingapp.StaffView.ViewShop.activity_viewshop;

public class home_page extends AppCompatActivity {
    private Button btn_my_product, btn_myOrder, btn_Promotions, btn_financial_report, btn_manage_users, btn_categories, btn_view_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home_page);
        // initialising all views through id defined above
        btn_my_product = findViewById(R.id.btn_my_product);
        btn_my_product = findViewById(R.id.btn_my_product);
        btn_myOrder = findViewById(R.id.btn_my_order);
        btn_Promotions = findViewById(R.id.btn_promotions);
        btn_financial_report = findViewById(R.id.btn_financial_report);
        btn_manage_users = findViewById(R.id.btn_manage_user);
        btn_view_shop = findViewById(R.id.btn_view_shop);
        btn_my_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình "MyProductActivity"
                Intent intent = new Intent(home_page.this, activity_MyProduct.class);
                startActivity(intent);
            }
        });
        btn_myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình "MyOrderActivity"
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                startActivity(intent);
            }
        });
        btn_view_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, activity_viewshop.class);
                startActivity(intent);
            }
        });
    }
}
