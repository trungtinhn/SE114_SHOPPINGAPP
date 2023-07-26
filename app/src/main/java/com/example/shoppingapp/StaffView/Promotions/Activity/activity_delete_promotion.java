package com.example.shoppingapp.StaffView.Promotions.Activity;

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
import com.example.shoppingapp.StaffView.Promotions.Adapter.PromotionDeleteAdapterStaff;
import com.example.shoppingapp.StaffView.Promotions.PromotionStaff;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_delete_promotion extends AppCompatActivity {
    private ImageButton btn_back;
    private Button btn_delete_promotion;
    private List<PromotionStaff> promotionList;
    private RecyclerView recyclerView;
    private PromotionDeleteAdapterStaff adapter;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_delete_promotion);
        btn_back = findViewById(R.id.imgbtn_back_delete);
        recyclerView = findViewById(R.id.RCV_promotions_delete);
        promotionList = new ArrayList<>();
        adapter = new PromotionDeleteAdapterStaff(promotionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        firestore = FirebaseFirestore.getInstance();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_delete_promotion.this,activity_promotions.class);
                startActivity(intent);
            }
        });
        btn_delete_promotion = findViewById(R.id.btn_delete_promotion);
        btn_delete_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PromotionStaff> selectedCategories = adapter.getSelectedCategories();
                if (selectedCategories.isEmpty()) {
                    Toast.makeText(activity_delete_promotion.this, "No categories selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (PromotionStaff categoryItem : selectedCategories) {
                    deletePromotion(categoryItem);
                }
            }
        });
        // Lấy danh mục từ Firestore và cập nhật danh sách
        FirebaseFirestore.getInstance().collection("KHUYENMAI")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String hinhAnhKM = document.getString("HinhAnhKM");
                            String hinhAnhTB = document.getString("HinhAnhTB");
                            String ChitietKM = document.getString("ChiTietKM");
                            String tenKM = document.getString("TenKM");
                            String loaiKhuyenMai = document.getString("LoaiKhuyenMai");
                            String maKM = document.getString("MaKM");
                            long donToiThieu = document.getLong("DonToiThieu");
                            double tiLe = document.getDouble("TiLe");
                            Timestamp ngayBatDau = document.getTimestamp("NgayBatDau");
                            Timestamp ngayKetThuc = document.getTimestamp("NgayKetThuc");
                            Boolean check = false;
                            PromotionStaff promotion = new PromotionStaff(tenKM, ChitietKM, loaiKhuyenMai, hinhAnhKM, maKM, donToiThieu, tiLe, ngayBatDau, ngayKetThuc, check, hinhAnhTB);
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
    private void deletePromotion(PromotionStaff categoryItem) {
        boolean check = categoryItem.getSelected();

        if (check) {
            String tenDMToDelete = categoryItem.getTenKM();

            firestore.collection("KHUYENMAI")
                    .whereEqualTo("TenKM", tenDMToDelete)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Get the MaKM of the promotion to be deleted
                                String maKMToDelete = document.getId();
                                // Delete the promotion document
                                document.getReference().delete();
                                // Delete related notifications in "THONGBAO" collection
                                firestore.collection("THONGBAO")
                                        .whereEqualTo("MaKM", maKMToDelete)
                                        .get()
                                        .addOnCompleteListener(notificationTask -> {
                                            if (notificationTask.isSuccessful()) {
                                                for (QueryDocumentSnapshot notificationDoc : notificationTask.getResult()) {
                                                    notificationDoc.getReference().delete();
                                                }
                                            } else {
                                                // Handle the failure if querying "THONGBAO" fails
                                                Toast.makeText(activity_delete_promotion.this, "Failed to delete related notifications", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            Toast.makeText(activity_delete_promotion.this, "Promotion and related notifications deleted successfully", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            Intent intent = new Intent(activity_delete_promotion.this, activity_promotions.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activity_delete_promotion.this, "Failed to delete category", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(activity_delete_promotion.this, "Cannot delete category with products, because this category has more than 2 products", Toast.LENGTH_SHORT).show();
        }
    }


}
