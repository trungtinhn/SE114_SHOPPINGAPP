package com.example.shoppingapp.customerview.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.product.ProductTrending;
import com.example.shoppingapp.customerview.product.ProductTrendingAdapter;
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
public class CategoriesDetails extends AppCompatActivity implements Filterable {
    private List<String> dataGiohang;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationCustomActivity bottomNavigationCustomActivity;
    private RecyclerView recyclerView;
    List<ProductTrending> productCardList;
    private ProductTrendingAdapter productAdapter;
    TextView textView;
    private String TenDM, MaDM;
    SearchView searchView;
    ImageView backICon, shoppingCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        if(intent != null){
            TenDM = intent.getStringExtra("TenDM");
            MaDM = intent.getStringExtra("MaDM");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catelogy_details);

        productAdapter = new ProductTrendingAdapter();

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rcvProduct);
        searchView = findViewById(R.id.searchView);
        backICon = findViewById(R.id.backIcon);
        textView = findViewById(R.id.txtNameCategori);
        shoppingCart = findViewById(R.id.ShoppingCart);
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);


        textView.setText(TenDM);

        SearchCategory();
        getDataCategories();
        SoLuongShoppingCart();

        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesDetails.this, ShoppingCart.class );
                startActivity(intent);
            }
        });
    }

    private void SearchCategory(){
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                productAdapter.getFilter().filter(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                productAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void getDataCategories(){
        db.collection("SANPHAM")
                .whereEqualTo("MaDM", MaDM)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        productCardList = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            String masp = documentSnapshot.getString("MaSP");
                            String name = documentSnapshot.getString("TenSP");
                            List<String> Anh = (List<String>) documentSnapshot.get("HinhAnhSP");
                            int price = documentSnapshot.getLong("GiaSP").intValue();
                            productCardList.add(new ProductTrending(masp, Anh.get(0), name, price));
                            Log.d("Errrrrrrrrr", name);
                        }
                        productAdapter.setData(productCardList);
                        productAdapter.setOnItemClick(new ProductTrendingAdapter.OnItemClick() {
                            @Override
                            public void onButtonItemClick(int position) {
                                Intent t = new Intent(CategoriesDetails.this, DetailProductActivity.class);
                                t.putExtra("MaSP", productCardList.get(position).getMasp());
                                startActivity(t);
                            }
                        });
                        recyclerView.setAdapter(productAdapter);
                    }
                });
    }
    private void SoLuongShoppingCart(){
        db.collection("GIOHANG")
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
                        BadgeDrawable badgeDrawable = BadgeDrawable.create(CategoriesDetails.this);
                        badgeDrawable.setNumber(dataGiohang.size());

                        BadgeUtils.attachBadgeDrawable(badgeDrawable, shoppingCart, null);
                    }
                });

    }

    @Override
    public Filter getFilter() {
        return null;
    }
}