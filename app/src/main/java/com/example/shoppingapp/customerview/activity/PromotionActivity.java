package com.example.shoppingapp.customerview.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.promotion.Promotion;
import com.example.shoppingapp.customerview.promotion.PromotionAdapter;
import com.example.shoppingapp.customerview.shoppingcart.Address;
import com.example.shoppingapp.customerview.shoppingcart.AddressAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity {
    FirebaseFirestore db;
    PromotionAdapter promotionAdapter;
    List<Promotion> dataPromotion;
    RecyclerView recyclerView;
    TextView btn_done;
    ImageView btn_back;
    String MaGG;
    String MaDC;
    String[] myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_customer);


        btn_back = findViewById(R.id.backIcon);
        btn_done = findViewById(R.id.btn_save);
        recyclerView = findViewById(R.id.rcyPromotion);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringArrayExtra("ListMaGH")!=null){
                myList = new String[intent.getStringArrayExtra("ListMaGH").length];
                myList = intent.getStringArrayExtra("ListMaGH");
            }
            if(intent.getStringExtra("MaDC") != null){
                MaDC = intent.getStringExtra("MaDC");
            }
        }

        db = FirebaseFirestore.getInstance();
        promotionAdapter = new PromotionAdapter(this.getApplicationContext());

        getDataPromotion();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(PromotionActivity.this, BuyNow.class);
                t.putExtra("ListMaGH", myList);
                if(MaDC!=null){
                    t.putExtra("MaDC", MaDC);
                }
                if(MaGG!=null){
                    t.putExtra("MaGG", MaGG );
                }
                startActivity(t);
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < dataPromotion.size(); i++){
                    if(dataPromotion.get(i).isCheck()){
                        MaGG = dataPromotion.get(i).getMaKM();
                    }
                }
                Intent t = new Intent(PromotionActivity.this, BuyNow.class);
                t.putExtra("ListMaGH", myList);
                if(MaDC!=null){
                    t.putExtra("MaDC", MaDC);
                }
                if(MaGG!=null){
                    t.putExtra("MaGG", MaGG );
                }
                Log.d("ErrrrrrrrrrrrrrrrrJdrotiuuuuuuuuuuu", MaGG);
                startActivity(t);
            }
        });
    }
    private void getDataPromotion(){
        db.collection("KHUYENMAI")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        dataPromotion = new ArrayList<>();
                        for(DocumentSnapshot doc : value.getDocuments()){
                            String MaKM = doc.getString("MaKM");
                            String TenKM = doc.getString("TenKM");
                            String ChiTietKM = doc.getString("ChiTietKM");
                            int DonTT = doc.getLong("DonToiThieu").intValue();
                            String HinhAnhKM = doc.getString("HinhAnhKM");
                            Double TiLe = doc.getDouble("TiLe");
                            String LoaiKM = doc.getString("LoaiKhuyenMai");
                            Timestamp NgayBD = doc.getTimestamp("NgayBatDau");
                            Timestamp NgayKT = doc.getTimestamp("NgayKetThuc");
                            dataPromotion.add(new Promotion(MaKM, TenKM, TiLe, HinhAnhKM, ChiTietKM, DonTT, LoaiKM, NgayBD, NgayKT, false));
                        }
                        promotionAdapter.setData(dataPromotion);
                        promotionAdapter.setCheckClick(new PromotionAdapter.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChange(int position) {
                                for(int i = 0; i < dataPromotion.size(); i++){
                                    dataPromotion.get(i).setCheck(false);
                                }
                                Promotion promotion = dataPromotion.get(position);
                                dataPromotion.get(position).setCheck(!promotion.isCheck());
                                promotionAdapter.setData(dataPromotion);
                            }
                        });
                        recyclerView.setAdapter(promotionAdapter);
                    }
                });
    }
}