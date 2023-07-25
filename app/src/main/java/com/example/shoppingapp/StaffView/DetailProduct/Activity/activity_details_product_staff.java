package com.example.shoppingapp.StaffView.DetailProduct.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Activity.activity_categories;
import com.example.shoppingapp.customerview.activity.DetailProductActivity;
import com.example.shoppingapp.customerview.color.ColorAdapter;
import com.example.shoppingapp.customerview.color.Colors;
import com.example.shoppingapp.customerview.size.SizeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class activity_details_product_staff extends AppCompatActivity {

    private ImageView backIcon;
    private ImageButton show;
    private TextView detail;
    private boolean isTextViewVisible = false;
    private String MaDM;
    private ImageView hinhanhSP;
    private TextView name, price ;
    private TextView description, review;
    private RecyclerView color, size;
    private Button trending;
    private String MaSP;
    DetailProductActivity detailProductActivity, detailProductActivity1;
    private Boolean check = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product_staff);

        MaDM = getIntent().getStringExtra("MaDM");
        MaSP = getIntent().getStringExtra("MaSP");
        backIcon = findViewById(R.id.icon_back);
        show = findViewById(R.id.show);
        detail = findViewById(R.id.txt_detail);
        hinhanhSP = findViewById(R.id.hinhanhSP);
        name = findViewById(R.id.name_product);
        price = findViewById(R.id.price_product);
        description = findViewById(R.id.txt_detail);
        review = findViewById(R.id.review);
        color = findViewById(R.id.RCV_color);
        color.setLayoutManager(new GridLayoutManager(this, 4));
        size = findViewById(R.id.RCV_size);
        size.setLayoutManager(new GridLayoutManager(this, 4));
        trending = findViewById(R.id.trending);
        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference donHangRef = FirebaseFirestore.getInstance().collection("SANPHAM");
                DocumentReference docRef = donHangRef.document(MaSP);
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Boolean trangThai = documentSnapshot.getBoolean("Trending");
                        boolean newTrangThai;
                        if (trangThai) {
                            newTrangThai = false;
                        } else if (!trangThai) {
                            newTrangThai = true;
                        }
                        else {
                            return;
                        }
                        docRef.update("Trending", newTrangThai)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Cập nhật thành công
                                            Toast.makeText(activity_details_product_staff.this, "Trending đã được cập nhật: " + String.valueOf(newTrangThai), Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Cập nhật thất bại
                                            Toast.makeText(activity_details_product_staff.this, "Trending cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });
            }
        });
        CollectionReference trendingfalseRef = FirebaseFirestore.getInstance().collection("SANPHAM");


        CollectionReference sanphamRef = FirebaseFirestore.getInstance().collection("SANPHAM");
        CollectionReference mausacRef = FirebaseFirestore.getInstance().collection("MAUSAC");
        CollectionReference sizeRef = FirebaseFirestore.getInstance().collection("SIZE");
        sanphamRef.whereEqualTo("MaSP", MaSP).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                if(!querySnapshot.isEmpty())
                {
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        String tenSP = documentSnapshot.getString("TenSP");
                        List<String> hinhanh = (List<String>) documentSnapshot.get("HinhAnhSP");
                        String hinhanhSPurl = hinhanh.get(0);
                        Picasso.get().load(hinhanhSPurl).into(hinhanhSP);
                        int giaSP = documentSnapshot.getLong("GiaSP") != null ? documentSnapshot.getLong("GiaSP").intValue() : 0;
                        String moTa = documentSnapshot.getString("MoTaSP");
                        description.setText(moTa);
                        price.setText(String.valueOf(giaSP));
                        name.setText(tenSP);

                        List<String> mauSacList = (List<String>) documentSnapshot.get("MauSac");
                        if (mauSacList != null && !mauSacList.isEmpty()) {
                            List<Colors>listmau = new ArrayList<>();
                            for (String mauSacId : mauSacList) {
                                // Truy vấn từng MauSac trong collection "MAUSAC" theo mã màu sắc (mauSacId)
                                mausacRef.document(mauSacId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot mausacDocumentSnapshot) {
                                        if (mausacDocumentSnapshot.exists()) {

                                            String tenMauSac = mausacDocumentSnapshot.getString("TenMau");
                                            String maMau = mausacDocumentSnapshot.getString("MaMau");
                                            String colorID = mausacDocumentSnapshot.getString("MaMauSac");
                                            Colors colors = new Colors(maMau,colorID, tenMauSac);
                                            listmau.add(colors);
                                            ColorAdapter colorAdapter = new ColorAdapter();
                                            colorAdapter.setData(listmau, detailProductActivity);
                                            color.setAdapter(colorAdapter);
                                        }
                                    }
                                });
                            }
                        }
                        List<String>sizeList = (List<String>) documentSnapshot.get("Size");
                        if(sizeList!=null && !sizeList.isEmpty())
                        {
                            List<String>sizeList1 = new ArrayList<>();
                            for (String sizeId : sizeList) {
                                sizeRef.document(sizeId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshotsize) {
                                        if(documentSnapshotsize.exists())
                                        {
                                            String Masize = documentSnapshotsize.getString("Size");
                                            sizeList1.add(Masize);
                                            SizeAdapter sizeAdapter = new SizeAdapter();
                                            sizeAdapter.setData(sizeList1, detailProductActivity);
                                            size.setAdapter(sizeAdapter);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nếu TextView đang ẩn, thì hiển thị nó và đặt biến isTextViewVisible thành true
                if (!isTextViewVisible) {
                    detail.setVisibility(View.VISIBLE);
                    isTextViewVisible = true;
                    show.setRotation(270);
                } else {
                    // Ngược lại, ẩn TextView và đặt biến isTextViewVisible thành false
                    detail.setVisibility(View.INVISIBLE);
                    isTextViewVisible = false;
                    show.setRotation(180);
                }
            }
        });

        setOnClickBackICon();
    }
    private void setOnClickBackICon() {

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_details_product_staff.this, activity_categories.class);
                intent.putExtra("MaDM", MaDM);
                startActivity(intent);
                finish();
            }
        });
    }
}