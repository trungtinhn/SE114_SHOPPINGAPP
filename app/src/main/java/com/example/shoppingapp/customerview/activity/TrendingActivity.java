package com.example.shoppingapp.customerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.product.ProductCard;
import com.example.shoppingapp.customerview.product.ProductCardAdapter;
import com.example.shoppingapp.customerview.product.customer_interface.IClickItemProductTrendingListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
@OptIn(markerClass = com.google.android.material.badge.ExperimentalBadgeUtils.class)
public class TrendingActivity extends AppCompatActivity {
    private List<String> dataGiohang;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private RecyclerView rcvProductTrending;

    ProductCardAdapter productCardAdapter;
    List<ProductCard> mTrendingCard;

    ImageView backICon, shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        rcvProductTrending = findViewById(R.id.rcvProductTrending);

        backICon = findViewById(R.id.backIcon);
        shoppingCart = findViewById(R.id.ShoppingCart);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setRcvProductTrending();
        setOnClickBackIcon();
        SoLuongShoppingCart();

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendingActivity.this, ShoppingCart.class );
                startActivity(intent);
            }
        });
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
        firebaseFirestore.collection("SANPHAM")
                .whereEqualTo("Trending",true)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.w("Error", "listen:error", error);
                                    return;
                                }
                                mTrendingCard = new ArrayList<>();
                                for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                    if (documentSnapshot.exists()) {
                                        String masp = documentSnapshot.getString("MaSP");
                                        String name = documentSnapshot.getString("TenSP");
                                        int price = documentSnapshot.getLong("GiaSP").intValue();
                                        List<String> Anh = (List<String>) documentSnapshot.get("HinhAnhSP");
                                        mTrendingCard.add(new ProductCard(Anh.get(0), name, price, masp));
                                    }
                                }
                                productCardAdapter.setData(mTrendingCard);
                                productCardAdapter.setOnItemClick(new ProductCardAdapter.OnItemClick() {
                                    @Override
                                    public void onButtonItemClick(int position) {
                                        Intent t = new Intent(TrendingActivity.this, DetailProductActivity.class);
                                        t.putExtra("MaSP", mTrendingCard.get(position).getMaSp());
                                        startActivity(t);
                                    }
                                });
                                rcvProductTrending.setAdapter(productCardAdapter);
                            }
                        });
    }
    private void SoLuongShoppingCart(){
        firebaseFirestore.collection("GIOHANG")
                .whereEqualTo("MaND", firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        dataGiohang = new ArrayList<>();
                        for(DocumentSnapshot doc: value.getDocuments()){
                            if(doc.exists()){
                                String ma = doc.getString("MaGH");
                                dataGiohang.add(ma);
                            }
                        }
                        BadgeDrawable badgeDrawable = BadgeDrawable.create(TrendingActivity.this);
                        badgeDrawable.setNumber(dataGiohang.size());

                        BadgeUtils.attachBadgeDrawable(badgeDrawable, shoppingCart, null);
                    }
                });

    }
}