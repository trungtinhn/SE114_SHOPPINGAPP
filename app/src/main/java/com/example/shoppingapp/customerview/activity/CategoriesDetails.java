package com.example.shoppingapp.customerview.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.categories.Categories;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.product.ProductAdapter;
import com.example.shoppingapp.customerview.product.ProductCard;
import com.example.shoppingapp.customerview.product.ProductTrending;
import com.example.shoppingapp.customerview.product.ProductTrendingAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDetails extends AppCompatActivity {
    private FirebaseFirestore db;
    private BottomNavigationCustomActivity bottomNavigationCustomActivity;
    private RecyclerView recyclerView;
    List<ProductTrending> productCardList;
    private ProductTrendingAdapter productAdapter;
    TextView textView;
    private String TenDM, MaDM;
    ImageView backICon;
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
        recyclerView = findViewById(R.id.rcvProduct);
        backICon = findViewById(R.id.backIcon);
        textView = findViewById(R.id.txtNameCategori);
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        textView.setText(TenDM);


        getDataCategories();

        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesDetails.this, BottomNavigationCustomActivity.class );
                startActivity(intent);
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
}