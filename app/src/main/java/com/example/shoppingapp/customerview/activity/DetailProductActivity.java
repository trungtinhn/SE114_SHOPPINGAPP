package com.example.shoppingapp.customerview.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.color.ColorAdapter;
import com.example.shoppingapp.customerview.color.Colors;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.size.SizeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView backIcon;
    private Product product;
    private TextView txtProductNameDetail;
    private TextView txtPriceProductDetail;
    private String maSP;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView revColor;
    private ColorAdapter colorAdapter;
    private List<Colors> colors;
    private  RecyclerView rcvSize;
    private SizeAdapter sizeAdapter;
    private List<String> sizes;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        txtProductNameDetail = findViewById(R.id.txtProductNameDetail);
        txtPriceProductDetail = findViewById(R.id.txtPriceProductDetail);
        revColor = findViewById(R.id.rcvCorlor);
        rcvSize = findViewById(R.id.rcvSize);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), RecyclerView.HORIZONTAL, false);
        revColor.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagerSize = new LinearLayoutManager(this.getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcvSize.setLayoutManager(linearLayoutManagerSize);

        firebaseFirestore = FirebaseFirestore.getInstance();

        backIcon = findViewById(R.id.backIcon);

        Intent intent = getIntent();
        maSP = intent.getStringExtra("MaSP");


        getProduct(maSP);

        //txtProductNameDetail.setText(maSP);



        setOnClickBackICon();
    }


    private void getProduct(String maSP){

        colorAdapter = new ColorAdapter();
        colors = new ArrayList<>();

        sizeAdapter =  new SizeAdapter();
        sizes = new ArrayList<>();


        final DocumentReference docRef = firebaseFirestore.collection("SANPHAM").document(maSP);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    String tenSP = snapshot.getString("TenSP");
                    Long giaSP = snapshot.getLong("GiaSP");
                    txtProductNameDetail.setText(tenSP);
                    txtPriceProductDetail.setText(String.valueOf(giaSP));

                    List<Colors> mauSacs = new ArrayList<>();
                    List<Map<String, Object>> colorMapList = (List<Map<String, Object>>) snapshot.get("MauSac");
                    for (Map<String, Object> colorMap : colorMapList) {
                        String maMau = (String) colorMap.get("MaMau");
                        String tenMau = (String) colorMap.get("TenMau");
                        String maMauSac = (String) colorMap.get("MaMauSac");
                        Colors mauSac = new Colors(maMau, maMauSac, tenMau);
                        mauSacs.add(mauSac);
                    }

                    sizes = (List<String>) snapshot.get("Size");

                    colorAdapter.setData(mauSacs);
                    revColor.setAdapter(colorAdapter);

                    sizeAdapter.setData(sizes);
                    rcvSize.setAdapter(sizeAdapter);
                   // setColor(mauSac);



                   // colorAdapter.setData(colors);
                  //  revColor.setAdapter(colorAdapter);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }


    private void setOnClickBackICon() {

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, BottomNavigationCustomActivity.class);
                startActivity(intent);
            }
        });
    }
}