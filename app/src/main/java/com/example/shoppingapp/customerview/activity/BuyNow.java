package com.example.shoppingapp.customerview.activity;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuyNow extends AppCompatActivity {
    FirebaseFirestore db;
    ImageView backbtn;
    String MaDC;
    String MaGG;
    String MaPTTT;
    String[] myList;
    List<com.example.shoppingapp.customerview.shoppingcart.ShoppingCart> myData;
    TextView Subtotal, Delivery, CheckOut, Total, ClickGiamGia, ClickAddress, ClickPayment;
    TextView TenSP, KichThuoc, MauSac, SoLuong, Price;
    ImageView imageProduct;
    ImageView NextProduct;
    LinearLayout HaveDC, DontHaveDC;
    int j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        j = 0;
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringArrayExtra("ListMaGH")!=null){
                myList = new String[intent.getStringArrayExtra("ListMaGH").length];
                myList = intent.getStringArrayExtra("ListMaGH");

                myData = new ArrayList<>();
            }
            if(intent.getStringExtra("MaDC") != null){
                MaDC = intent.getStringExtra("MaDC");
            }
        }
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        HaveDC = findViewById(R.id.layout_address);
        DontHaveDC = findViewById(R.id.layout_not_address);
        imageProduct = findViewById(R.id.image_product);
        TenSP = findViewById(R.id.txt_tensp);
        MauSac = findViewById(R.id.txt_MauSac);
        KichThuoc = findViewById(R.id.txt_KichThuoc);
        Price = findViewById(R.id.txt_giaban);
        SoLuong = findViewById(R.id.txt_soluong);
        NextProduct = findViewById(R.id.image_next);
        if(MaDC == null){
            HaveDC.setVisibility(View.GONE);
        }
        else{
            DontHaveDC.setVisibility(View.GONE);
        }
        if(myList[1] == null) NextProduct.setVisibility(View.INVISIBLE);

        backbtn = findViewById(R.id.backIcon);
        Subtotal = findViewById(R.id.subtotal_text);
        Delivery = findViewById(R.id.delivery_text);
        CheckOut = findViewById(R.id.btn_checkout);
        ClickGiamGia = findViewById(R.id.click_giamgia);
        ClickAddress = findViewById(R.id.click_address);
        ClickPayment = findViewById(R.id.click_payment);
        ClickPayment = findViewById(R.id.click_payment);

        getDataProduct();

        NextProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if( j >= myData.size()){
                    j = 0;
                }
                Picasso.get().load(myData.get(j).getDataImage()).into(imageProduct);
                TenSP.setText(myData.get(j).getTenSanPham());
                MauSac.setText(myData.get(j).getMauSac());
                KichThuoc.setText(myData.get(j).getSize());
                SoLuong.setText(String.valueOf("x"+myData.get(j).getSoLuong()));
                Price.setText(String.valueOf(myData.get(j).getGiaTien()));
            }
        });

        ClickPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, CheckOut.class);
                startActivity(t);
            }
        });
        ClickAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, SaveAddress.class);
                startActivity(t);
            }
        });
        ClickGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(BuyNow.this, )
                //startActivity(t);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent(BuyNow.this, ShoppingCart.class);
                startActivity(t);
            }
        });
        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, DoneActivity.class);
                startActivity(t);

            }
        });
    }
    private void getDataProduct(){
        try{
            if(myList != null){
                for(int i = 0; i < myList.length; i++){
                    Log.d("Ma GIo Hang HHHHHHHHHHHHHHHHHH", myList[i]);
                    db.collection("GIOHANG")
                            .whereEqualTo("MaGH",myList[i])
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String TenSP = doc.getString("TenSP");
                                            String Size = doc.getString("Size");
                                            int SoLuong = doc.getLong("SoLuong").intValue();
                                            List<String> Anh = (List<String>) doc.get("HinhAnhSP");
                                            int GiaSP = doc.getLong("GiaSP").intValue();
                                            int GiaTien = doc.getLong("GiaTien").intValue();
                                            String MauSac = doc.getString("MauSac");
                                            String MaGH = doc.getString("MaGH");
                                            String MaSP = doc.getString("MaSP");
                                            boolean check = false;
                                            Log.d("Errrrrrrrrrr", Anh.get(0));
                                            myData.add(new com.example.shoppingapp.customerview.shoppingcart.ShoppingCart(MaSP, MaGH, Anh.get(0), TenSP, GiaSP, SoLuong, GiaTien, Size, MauSac, check));
                                        }

                                        Picasso.get().load(myData.get(0).getDataImage()).into(imageProduct);
                                        TenSP.setText(myData.get(0).getTenSanPham());
                                        MauSac.setText(myData.get(0).getMauSac());
                                        KichThuoc.setText(myData.get(0).getSize());
                                        SoLuong.setText(String.valueOf("x"+myData.get(0).getSoLuong()));
                                        Price.setText(String.valueOf(myData.get(0).getGiaTien()));

                                    } else {
                                        Log.d("Error", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            }
        }catch (Exception e){

        }

    }
}