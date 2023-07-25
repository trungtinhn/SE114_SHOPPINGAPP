package com.example.shoppingapp.StaffView.Promotions.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.example.shoppingapp.StaffView.Promotions.Adapter.PromotionAdapterStaff;
import com.example.shoppingapp.StaffView.Promotions.PromotionStaff;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_promotions extends AppCompatActivity {
    private ImageButton btn_back;
    private Button btn_add_new, btn_delete;
    private List<PromotionStaff> promotionList;
    private RecyclerView recyclerView;
    private PromotionAdapterStaff adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_promotions);
        btn_back = findViewById(R.id.imgbtn_back);
        btn_add_new=findViewById(R.id.btn_add_new_promotions);
        recyclerView = findViewById(R.id.RCV_promotions);
        promotionList = new ArrayList<>();
        adapter = new PromotionAdapterStaff(promotionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        btn_delete = findViewById(R.id.btn_delete_promotion);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_promotions.this, home_page.class);
                startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_promotions.this, activity_delete_promotion.class);
                startActivity(intent);
            }
        });
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_promotions.this, activity_add_new_promotions.class);
                startActivity(intent);
            }
        });
        // Lấy danh mục từ Firestore và cập nhật danh sách
        FirebaseFirestore.getInstance().collection("KHUYENMAI")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String hinhAnhKM = document.getString("HinhAnhKM");
                            String ChitietKM = document.getString("ChiTietKM");
                            String tenKM = document.getString("TenKM");
                            String loaiKhuyenMai = document.getString("LoaiKhuyenMai");
                            String maKM = document.getString("MaKM");
                            long donToiThieu = document.getLong("DonToiThieu");
                            double tiLe = document.getDouble("TiLe");
                            Timestamp ngayBatDau = document.getTimestamp("NgayBatDau");
                            Timestamp ngayKetThuc = document.getTimestamp("NgayKetThuc");
                            PromotionStaff promotion = new PromotionStaff(tenKM, ChitietKM, loaiKhuyenMai, hinhAnhKM, maKM, donToiThieu, tiLe, ngayBatDau, ngayKetThuc);
                            promotionList.add(promotion);
                        }
                        adapter.notifyDataSetChanged();
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

}
