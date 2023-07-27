package com.example.shoppingapp.StaffView.MyProduct.Fragment;

import static android.content.Context.SEARCH_SERVICE;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyProduct.Adapter.My_inventory_Adapter;
import com.example.shoppingapp.StaffView.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class my_inventory_fragment extends Fragment implements Filterable {

    private RecyclerView recyclerView;
    private My_inventory_Adapter adapter;
    private SearchView searchView;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_inventory, container, false);

        recyclerView = view.findViewById(R.id.RCV_My_inventory);
        searchView = view.findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();
        adapter = new My_inventory_Adapter(productList, getContext());
        adapter.setHideButtonClickListener(new My_inventory_Adapter.HideButtonClickListener() {
            @Override
            public void onHideButtonClick(int position) {
                adapter.updateProductStatus(position);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        loadProductsFromFirestore();
        SearchTrending();
        return view;
    }
    private void SearchTrending(){
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
    private void loadProductsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sanphamRef = db.collection("SANPHAM");
        sanphamRef.whereEqualTo("TrangThai", "Inventory")
                .whereGreaterThan("SoLuongConLai", 0)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        // Xử lý lỗi nếu cần thiết
                        return;
                    }

                    productList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String tenSP = document.getString("TenSP");
                        String maSP = document.getString("MaSP");
                        String moTaSP = document.getString("MoTaSP");
                        List<String> color = (List<String>) document.get("MauSac");
                        List<String> size = (List<String>) document.get("Size");
                        String trangthai = document.getString("TrangThai");
                        int price = document.getLong("GiaSP") != null ? document.getLong("GiaSP").intValue() : 0;
                        int warehouse = document.getLong("SoLuongConLai") != null ? document.getLong("SoLuongConLai").intValue() : 0;
                        int sold = document.getLong("SoLuongDaBan") != null ? document.getLong("SoLuongDaBan").intValue() : 0;
                        int love = document.getLong("SoLuongYeuThich") != null ? document.getLong("SoLuongYeuThich").intValue() : 0;
                        int view = document.getLong("SoLuongXem") != null ? document.getLong("SoLuongXem").intValue() : 0;






                        // Kiểm tra xem trường "HinhAnhSP" có dữ liệu không
                        if (document.contains("HinhAnhSP")) {
                            Object hinhAnhSPValue = document.get("HinhAnhSP");

                            // Kiểm tra nếu giá trị là một List<String>
                            if (hinhAnhSPValue instanceof List) {
                                List<String> hinhAnhSPList = (List<String>) hinhAnhSPValue;
                                // Lấy địa chỉ ảnh đầu tiên trong mảng
                                String hinhAnhSP = hinhAnhSPList != null && !hinhAnhSPList.isEmpty() ? hinhAnhSPList.get(0) : "";
                                // Tạo đối tượng Product và thiết lập các thuộc tính
                                Product product = new Product(hinhAnhSP, tenSP, price, warehouse, sold, love, view, maSP);
                                product.setMoTaSP(moTaSP);
                                product.setSize(size);
                                product.setMauSac(color);
                                product.setTrangThai(trangthai);

                                // Thêm đối tượng Product vào danh sách
                                productList.add(product);
                            } else if (hinhAnhSPValue instanceof String) {
                                // Xử lý khi giá trị là một String
                                String hinhAnhSP = (String) hinhAnhSPValue;
                                // Tạo đối tượng Product và thiết lập các thuộc tính
                                Product product = new Product(hinhAnhSP, tenSP, price, warehouse, sold, love, view, maSP);
                                product.setMoTaSP(moTaSP);
                                product.setSize(size);
                                product.setMauSac(color);
                                product.setTrangThai(trangthai);
                                // Thêm đối tượng Product vào danh sách
                                productList.add(product);
                            } else {
                                // Xử lý trường hợp giá trị không phải là List<String> hoặc String
                                // Nếu trường "HinhAnhSP" có giá trị khác kiểu List<String> hoặc String,
                                // bạn có thể xử lý tại đây theo ý muốn.
                            }
                        } else {
                            // Xử lý trường hợp không có dữ liệu trong trường "HinhAnhSP"
                            // Nếu không có giá trị hoặc giá trị là null, bạn có thể xử lý tại đây theo ý muốn.
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }


    @Override
    public Filter getFilter() {
        return null;
    }


}
