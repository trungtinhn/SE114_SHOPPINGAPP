package com.example.shoppingapp.StaffView.ViewShop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;
import com.example.shoppingapp.StaffView.DetailProduct.Activity.activity_details_category_view;
import com.example.shoppingapp.StaffView.ViewShop.Adapter.list_adapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class fragment_listItems extends Fragment {
    private RecyclerView recyclerView;
    private list_adapter adapter;
    private List<CategoryItem> categoryItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list_item, container, false);

        recyclerView = view.findViewById(R.id.RCV_item_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        categoryItemList = new ArrayList<>();
        adapter = new list_adapter(categoryItemList, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new list_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryItem categoryItem) {
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
                                Intent intent = new Intent(getActivity(), activity_details_category_view.class);
                                intent.putExtra("MaDM", categoryId);
                                startActivity(intent);
                            }
                        });
            }
        });
        // Load products from Firebase
        loadProducts();

        return view;
    }

    private void loadProducts() {
        // Query the "SANPHAM" collection in Firebase
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
