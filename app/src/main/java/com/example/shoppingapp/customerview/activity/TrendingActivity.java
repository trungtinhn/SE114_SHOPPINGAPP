package com.example.shoppingapp.customerview.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductTrendingListener;
import com.example.shoppingapp.customerview.product.ProductCard;
import com.example.shoppingapp.customerview.product.ProductCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TrendingActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView rcvProductTrending;

    ProductCardAdapter productCardAdapter;
    List<ProductCard> mTrendingCard;

    ImageView backICon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        rcvProductTrending = findViewById(R.id.rcvProductTrending);

        backICon = findViewById(R.id.backIcon);
        firebaseFirestore = FirebaseFirestore.getInstance();

        setRcvProductTrending();
        setOnClickBackIcon();
    }
    private void setOnClickBackIcon() {
        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendingActivity.this, BottomNavigationCustomActivity.class );
                startActivity(intent);
            }
        });

    }

    private void setRcvProductTrending() {
        productCardAdapter = new ProductCardAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rcvProductTrending.setLayoutManager(gridLayoutManager);

        mTrendingCard = new ArrayList<>();
        firebaseFirestore.collection("GIOHANG")
                .whereEqualTo("MaND","ND001")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.w("Error", "listen:error", error);
                                    return;
                                }
                                for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                    if (documentSnapshot.exists()) {
                                        String masp = documentSnapshot.getString("MaSP");
                                        String name = documentSnapshot.getString("TenSP");
                                        int price = documentSnapshot.getLong("GiaSP").intValue();
                                        List<String> Anh = (List<String>) documentSnapshot.get("HinhAnhSP");
                                        mTrendingCard.add(new ProductCard(Anh.get(0), name, price, masp));
                                    }
                                }
                                productCardAdapter.setData(mTrendingCard, new IClickItemProductTrendingListener() {
                                    @Override
                                    public void onClickItemProductTrending(ProductCard productCard) {
                                        Intent t = new Intent(TrendingActivity.this, DetailProductActivity.class);
                                        t.putExtra("MaSP", productCard.getMaSp());
                                        startActivity(t);
                                    }
                                });
                                rcvProductTrending.setAdapter(productCardAdapter);
                            }
                        });
    }
}