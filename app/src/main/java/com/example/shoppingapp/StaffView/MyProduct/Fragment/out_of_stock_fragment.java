package com.example.shoppingapp.StaffView.MyProduct.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyProduct.Adapter.My_oos_Adapter;
import com.example.shoppingapp.StaffView.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class out_of_stock_fragment extends Fragment {

    private RecyclerView recyclerView;
    private My_oos_Adapter adapter;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oos, container, false);

        recyclerView = view.findViewById(R.id.RCV_My_oos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();
        adapter = new My_oos_Adapter(productList, getContext());
        recyclerView.setAdapter(adapter);
        loadProductsFromFirestore();
        return view;
    }
    private void loadProductsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sanphamRef = db.collection("SANPHAM");

        sanphamRef.whereEqualTo("SoLuongConLai", 0)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        // Xử lý lỗi nếu cần thiết
                        return;
                    }

                    productList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        List<String> hinhAnhSPList = (List<String>) document.get("HinhAnhSP");

                        // Lấy địa chỉ ảnh đầu tiên trong mảng
                        String hinhAnhSP = hinhAnhSPList != null && !hinhAnhSPList.isEmpty() ? hinhAnhSPList.get(0) : "";
                        String tenSP = document.getString("TenSP");
                        String MaSP  =  document.getString("MaSP");
                        int price = document.getLong("GiaSP") != null ? document.getLong("GiaSP").intValue() : 0;
                        int warehouse = document.getLong("SoLuongConLai") != null ? document.getLong("SoLuongConLai").intValue() : 0;
                        int sold = document.getLong("SoLuongDaBan") != null ? document.getLong("SoLuongDaBan").intValue() : 0;
                        int love = document.getLong("SoLuongYeuThich") != null ? document.getLong("SoLuongYeuThich").intValue() : 0;
                        int view = document.getLong("SoLuongXem") != null ? document.getLong("SoLuongXem").intValue() : 0;
                        Product product = new Product(hinhAnhSP, tenSP, price, warehouse, sold, love, view, MaSP);
                        // Thêm đối tượng Product vào danh sách
                        productList.add(product);
                    }

                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromFirestore();
    }

}
