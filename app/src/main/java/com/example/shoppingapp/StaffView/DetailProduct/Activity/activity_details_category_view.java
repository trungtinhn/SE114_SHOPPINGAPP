package com.example.shoppingapp.StaffView.DetailProduct.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.shoppingapp.StaffView.ViewShop.Activity.activity_viewshop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class activity_details_category_view extends AppCompatActivity implements Filterable {
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
                Intent intent = new Intent(activity_details_category_view.this, activity_viewshop.class);
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
        adapter.setOnItemClickListener(new adapter_details_category.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý sự kiện khi nhấp vào danh mục
                String TenSP = product.getName(); // Lấy tên danh mục
                Log.d("ProductID", "ProductID: " + TenSP);

                // Tạo một truy vấn Firestore để lấy MaDM từ TenDM
                FirebaseFirestore.getInstance().collection("SANPHAM")
                        .whereEqualTo("TenSP", TenSP)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                // Lấy MaDM từ kết quả truy vấn
                                String productID = task.getResult().getDocuments().get(0).getId();
                                Intent intent = new Intent(activity_details_category_view.this, activity_details_product_staff.class);
                                intent.putExtra("MaSP", productID);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });

    }
    @Override
    public Filter getFilter() {
        return null;
    }
}
