package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shoppingapp.categories.Categories;
import com.example.shoppingapp.categories.CategoriesAdapter;
import com.example.shoppingapp.product.Product;
import com.example.shoppingapp.product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationCustomActivity extends AppCompatActivity {

    private RecyclerView rcvProduct;
    private List<Product> listProduct;
    private ProductAdapter productAdapter;

    private RecyclerView rcvCategories;
    private List<Categories> listCategories;
    private CategoriesAdapter categoriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_custom);

        setDataRcvProduct();
        setDataRcvCategories();


    }

    private void setDataRcvCategories() {
        rcvCategories = findViewById(R.id.rcvCategories);

        listCategories = new ArrayList<>();

        listCategories.add(new Categories("Thời trang nam"));
        listCategories.add(new Categories("Thời trang nữ"));
        listCategories.add(new Categories("Phụ kiện"));
        listCategories.add(new Categories("Giày nam"));
        listCategories.add(new Categories("Giày nữ"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.setData(listCategories);
        rcvCategories.setAdapter(categoriesAdapter);
    }

    private void setDataRcvProduct() {
        rcvProduct = findViewById(R.id.rcvProduct);

        listProduct = new ArrayList<>();

        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter();

        productAdapter.setData(listProduct);

        rcvProduct.setAdapter(productAdapter);
    }
}