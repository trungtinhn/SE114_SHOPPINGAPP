package com.example.shoppingapp.StaffView.Categories.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Adapter.adapter_details_category;
import com.example.shoppingapp.StaffView.Product;

import java.util.ArrayList;
import java.util.List;

public class activity_details_category extends AppCompatActivity implements Filterable {
    private RecyclerView recyclerView;
    private adapter_details_category adapter;
    private List<Product> productList;

    private Button button;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_category);

        // Lấy categoryId từ Intent
        String categoryId = getIntent().getStringExtra("MaDM");

        recyclerView = findViewById(R.id.RCV_Details_Category);
        productList = new ArrayList<>();
        adapter = new adapter_details_category(productList, categoryId);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        button = findViewById(R.id.backIcon);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_details_category.this, activity_categories.class);
                startActivity(intent);
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
