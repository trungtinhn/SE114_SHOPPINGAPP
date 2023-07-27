package com.example.shoppingapp.StaffView.ViewShop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.DetailProduct.Activity.activity_details_viewshop;
import com.example.shoppingapp.StaffView.Product;
import com.example.shoppingapp.StaffView.ViewShop.Adapter.product_adapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

public class fragment_products extends Fragment {
    private RecyclerView recyclerView;
    private product_adapter adapter1;
    private List<Product> productList1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list_product, container, false);

        recyclerView = view.findViewById(R.id.RCV_product_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productList1 = new ArrayList<>();
        CollectionReference sanphamRef = FirebaseFirestore.getInstance().collection("SANPHAM");
        sanphamRef.whereEqualTo("TrangThai", "Inventory").get(Source.SERVER)
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                productList1.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    List<String> hinhAnhSPList = (List<String>) document.get("HinhAnhSP");

                                    // Lấy địa chỉ ảnh đầu tiên trong mảng
                                    String hinhAnhSP = hinhAnhSPList != null && !hinhAnhSPList.isEmpty() ? hinhAnhSPList.get(0) : "";
                                    String tenSP = document.getString("TenSP");
                                    int giaSP = document.getLong("GiaSP") != null ? document.getLong("GiaSP").intValue() : 0;
                                    // Tạo đối tượng Product từ dữ liệu lấy được
                                    Product product = new Product(hinhAnhSP, tenSP, giaSP);
                                    // Thêm đối tượng Product vào danh sách
                                    productList1.add(product);

                                }

                                adapter1.notifyDataSetChanged();
                            } else {
                                // Xử lý khi không thành công
                                Exception exception = task.getException();
                                // ...
                            }

                        });

        adapter1 = new product_adapter(productList1, view.getContext());
        recyclerView.setAdapter(adapter1);
        // Load products from Firebase
        adapter1.setOnItemClickListener(new product_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
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
                                Intent intent = new Intent(getActivity(), activity_details_viewshop.class);
                                intent.putExtra("MaSPP", productID);
                                startActivity(intent);
                            }
                        });
            }
        });


        return view;
    }

}
