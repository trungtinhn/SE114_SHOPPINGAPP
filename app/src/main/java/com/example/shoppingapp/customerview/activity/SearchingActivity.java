package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.product.ProductCard;
import com.example.shoppingapp.customerview.product.ProductCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends AppCompatActivity {
    private RecyclerView rcvSearching;

    ProductCardAdapter productCardAdapter;
    List<ProductCard> mSearching;
    ImageView backICon;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        rcvSearching = findViewById(R.id.rcvSearching);
        backICon = findViewById(R.id.backIcon);

        editSearch = findViewById(R.id.editSearch);


        setRcvProductSearching();
        setOnClickBackIcon();

    }
    private void setOnClickBackIcon() {
        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingActivity.this, BottomNavigationCustomActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setRcvProductSearching() {
        productCardAdapter = new ProductCardAdapter();
        mSearching = new ArrayList<>();

        mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 1", 299999));
        mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 2", 299999));

        mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));

        mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));
        mSearching.add(new ProductCard(R.drawable.mauaodep, "Giay loai 3", 299999));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rcvSearching.setLayoutManager(gridLayoutManager);
        productCardAdapter.setData(mSearching);
        rcvSearching.setAdapter(productCardAdapter);
    }
}