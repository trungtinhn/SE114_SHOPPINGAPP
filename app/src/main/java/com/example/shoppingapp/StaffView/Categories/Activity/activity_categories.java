package com.example.shoppingapp.StaffView.Categories.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Adapter.adapter_categories;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_categories extends AppCompatActivity {
    private ImageButton back;

    private Button btn;
    private Button btn_delete_categories;
    private RecyclerView rcvCategories;
    private adapter_categories adapter;
    private List<CategoryItem> categoryItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        back = findViewById(R.id.backIcon);
        rcvCategories = findViewById(R.id.rcvCategories);
        categoryItemList = new ArrayList<>();
        adapter = new adapter_categories(categoryItemList);
        rcvCategories.setLayoutManager(new LinearLayoutManager(this));
        rcvCategories.setAdapter(adapter);
        btn = findViewById(R.id.btn_create_new_category);
        btn_delete_categories = findViewById(R.id.btn_delete_category);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_categories.this, home_page.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_categories.this, activity_add_new_category.class);
                startActivity(intent);
            }
        });
        btn_delete_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_categories.this, activity_delete_categories.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new adapter_categories.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryItem categoryItem) {
                // Xử lý sự kiện khi nhấp vào danh mục
                String categoryName = categoryItem.getName(); // Lấy tên danh mục

                // Tạo một truy vấn Firestore để lấy MaDM từ TenDM
                FirebaseFirestore.getInstance().collection("DANHMUC")
                        .whereEqualTo("TenDM", categoryName)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                // Lấy MaDM từ kết quả truy vấn
                                String categoryId = task.getResult().getDocuments().get(0).getId();

                                // Chuyển sang màn hình hiển thị sản phẩm với categoryId
                                Intent intent = new Intent(activity_categories.this, activity_details_category.class);
                                intent.putExtra("MaDM", categoryId);
                                startActivity(intent);
                            }
                        });
            }
        });

        // Lấy danh mục từ Firestore và cập nhật danh sách
        FirebaseFirestore.getInstance().collection("DANHMUC")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("TenDM");
                            String image = document.getString("AnhDM");
                            int quantity = document.getLong("SoLuongSP").intValue();
                            CategoryItem categoryItem = new CategoryItem(name, image, quantity);
                            categoryItemList.add(categoryItem);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
