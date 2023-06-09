package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.product.ProductCard;
import com.example.shoppingapp.customerview.product.ProductCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendingActivity extends AppCompatActivity {

    private RecyclerView rcvProductTrending;

    ProductCardAdapter productCardAdapter;
    List<ProductCard> mTrendingCard;

    ImageView backICon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        rcvProductTrending = findViewById(R.id.rcvProductTrending);

        backICon = findViewById(R.id.backIcon);

        setRcvProductTrending();
        setOnClickBackIcon();
    }
    private void setOnClickBackIcon() {
        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendingActivity.this, BottomNavigationCustomActivity.class );
                startActivity(intent);
            }
        });

    }

    private void setRcvProductTrending() {
        productCardAdapter = new ProductCardAdapter();
        mTrendingCard = new ArrayList<>();



        mTrendingCard.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 1", 299999));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rcvProductTrending.setLayoutManager(gridLayoutManager);
        productCardAdapter.setData(mTrendingCard);
        rcvProductTrending.setAdapter(productCardAdapter);

    }
}