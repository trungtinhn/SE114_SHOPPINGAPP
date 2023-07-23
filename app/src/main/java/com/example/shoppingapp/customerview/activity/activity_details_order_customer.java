package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.Adapter.ProductAdapter_customer;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class activity_details_order_customer extends AppCompatActivity {


    private ImageView back;
    private List<ItemOrder> itemOrderList;
    private TextView TenNguoiMua, TenND, MaND, SDT, Diachi;

    private RecyclerView recyclerViewProducts;
    private ProductAdapter_customer productAdapter;
    private ImageView img_avatar;

    public activity_details_order_customer() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order_customer);

        back = findViewById(R.id.back_customer);
        TenNguoiMua = findViewById(R.id.tennguoimua_customer);
        MaND  = findViewById(R.id.maND_customer);
        img_avatar = findViewById(R.id.img_avatar_customer);
        Diachi = findViewById(R.id.diachi_chitiet_customer);
        recyclerViewProducts = findViewById(R.id.list_sanpham_customer);
        itemOrderList = new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });
        String maDH = getIntent().getStringExtra("MaDH");
        FirebaseFirestore db_donhang = FirebaseFirestore.getInstance();
        FirebaseFirestore db_nguoidung = FirebaseFirestore.getInstance();
        FirebaseFirestore db_khuyenmai = FirebaseFirestore.getInstance();
        FirebaseFirestore db_diachi = FirebaseFirestore.getInstance();
        FirebaseFirestore db_dathang = FirebaseFirestore.getInstance();
        FirebaseFirestore db_sanpham = FirebaseFirestore.getInstance();
        CollectionReference donHangRef = db_donhang.collection("DONHANG");
        DocumentReference docRef = donHangRef.document(maDH);

        // Truy vấn dữ liệu từ Firebase
        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String tenNguoiMua = documentSnapshot.getString("TenNguoiMua");
                        TenNguoiMua.setText(tenNguoiMua);
                        String maND = documentSnapshot.getString("maND");
                        MaND.setText(maND);
                        String MaDC = documentSnapshot.getString("MaDC");

                        String MaDH = documentSnapshot.getString("MaDH");

                        // Từ đây, xuất ra nhiều collection khác
                        CollectionReference nguoidung = db_nguoidung.collection("NGUOIDUNG");
                        DocumentReference nguoidungref = nguoidung.document(maND);
                        nguoidungref.get().addOnCompleteListener(nguoidungdocument->{
                            if(nguoidungdocument.getResult().exists())
                            {
                                String anhDaiDien = nguoidungdocument.getResult().getString("avatar");
                                Picasso.get().load(anhDaiDien).into(img_avatar);
                            }
                        });
                        CollectionReference diachi = db_diachi.collection("DIACHI");
                        DocumentReference diachiRef = diachi.document(MaDC);
                        diachiRef.get().addOnCompleteListener(diachiDocument -> {
                            if(diachiDocument.getResult().exists())
                            {
                                String tenduong = diachiDocument.getResult().getString("DiaChi");
                                String phuongxa = diachiDocument.getResult().getString("PhuongXa");
                                String quanhuyen = diachiDocument.getResult().getString("QuanHuyen");
                                String tinhTP = diachiDocument.getResult().getString("TinhThanhPho");
                                String DiaChi = tenduong + ", " + phuongxa + ", " + quanhuyen + ", " + tinhTP;
                                Diachi.setText(DiaChi);
                            }
                        });


                        CollectionReference dathangRef = db_dathang.collection("DATHANG");
                        dathangRef.whereEqualTo("MaDH", MaDH)
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    List<ItemOrder> itemOrderList = new ArrayList<>();
                                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                        String maSP = document.getString("MaSP");

                                        int number = document.getLong("SoLuong") != null ? Math.toIntExact(document.getLong("SoLuong")) : 0;

                                        // Truy vấn Firebase để lấy thông tin sản phẩm từ MaSP
                                        CollectionReference sanphamRef = db_dathang.collection("SANPHAM");
                                        sanphamRef.document(maSP).get()
                                                .addOnSuccessListener(sanphamDocument -> {
                                                    if (sanphamDocument.exists()) {
                                                        String tenSP = sanphamDocument.getString("TenSP");
                                                        String hinhAnhSP = null;
                                                        List<String> hinhAnhSPList = (List<String>) sanphamDocument.get("HinhAnhSP");
                                                        if (hinhAnhSPList != null && !hinhAnhSPList.isEmpty()) {
                                                            hinhAnhSP = hinhAnhSPList.get(0); // Lấy phần tử đầu tiên trong mảng
                                                        }
                                                        else {

                                                            // Xử lý khi giá trị `HinhAnhSP` là null hoặc mảng rỗng
                                                        }
                                                        Long giaSPLong = sanphamDocument.getLong("GiaSP");
                                                        int GiaSP = giaSPLong != null ? Math.toIntExact(giaSPLong) : 0;

                                                        ItemOrder itemOrder = new ItemOrder(hinhAnhSP, tenSP, maSP, GiaSP, number);
                                                        itemOrderList.add(itemOrder);
                                                        // Cập nhật tổng tiền
                                                        int totalPrice = GiaSP * number;

                                                        productAdapter = new ProductAdapter_customer(itemOrderList);
                                                        recyclerViewProducts.setAdapter(productAdapter);
                                                        recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 1));

                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Xử lý khi truy vấn thất bại
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Xử lý khi truy vấn thất bại
                                });
                    } else {
                        // Dữ liệu không tồn tại
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });
    }

}