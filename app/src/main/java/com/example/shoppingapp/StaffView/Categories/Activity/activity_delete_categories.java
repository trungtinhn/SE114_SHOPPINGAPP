package com.example.shoppingapp.StaffView.Categories.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Adapter.adapter_delete_categories;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class activity_delete_categories extends AppCompatActivity {
    private ImageButton back;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private Button btn;
    private RecyclerView rcvCategories;
    private adapter_delete_categories adapter;
    private List<CategoryItem> categoryItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_categories);

        back = findViewById(R.id.backIcon);
        rcvCategories = findViewById(R.id.rcvdeleteCategories);
        categoryItemList = new ArrayList<>();
        adapter = new adapter_delete_categories(categoryItemList);
        rcvCategories.setLayoutManager(new LinearLayoutManager(this));
        rcvCategories.setAdapter(adapter);
        btn = findViewById(R.id.btn_delete_category);
        firestore = FirebaseFirestore.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_delete_categories.this, activity_categories.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CategoryItem> selectedCategories = adapter.getSelectedCategories();
                if (selectedCategories.isEmpty()) {
                    Toast.makeText(activity_delete_categories.this, "No categories selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CategoryItem categoryItem : selectedCategories) {
                    deleteCategory(categoryItem);
                }
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
    private void deleteCategory(CategoryItem categoryItem) {
        int soLuongSP = categoryItem.getQuantity();

        if (soLuongSP == 0) {
            String tenDMToDelete = categoryItem.getName();

            firestore.collection("DANHMUC")
                    .whereEqualTo("TenDM", tenDMToDelete)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            Toast.makeText(activity_delete_categories.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            Intent intent = new Intent(activity_delete_categories.this, activity_categories.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activity_delete_categories.this, "Failed to delete category", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(activity_delete_categories.this, "Cannot delete category with products, because this category have more than 2 products", Toast.LENGTH_SHORT).show();
        }
    }

}
