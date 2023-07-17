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
import com.example.shoppingapp.customerview.shoppingcart.Address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuyNow extends AppCompatActivity {
    List<Address> list;
    FirebaseFirestore db;
    ImageView backbtn;
    String MaDC;
    String MaGG;
    String[] myList;
    List<com.example.shoppingapp.customerview.shoppingcart.ShoppingCart> myData;
    TextView Subtotal, Delivery, CheckOut, Total, ClickGiamGia, ClickAddress, ClickPayment;
    //
    TextView TenSP, KichThuoc, MauSac, SoLuong, Price;
    ImageView imageProduct;
    ImageView NextProduct;
    LinearLayout HaveDC, DontHaveDC;
    //
    TextView Ten, SDT, Duong, DC;
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
            if(intent.getStringExtra("MaGG") != null){
                MaGG = intent.getStringExtra(MaGG);
            }
        }
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        HaveDC = findViewById(R.id.layout_address);
        DontHaveDC = findViewById(R.id.layout_not_address);
        //
        imageProduct = findViewById(R.id.image_product);
        TenSP = findViewById(R.id.txt_tensp);
        MauSac = findViewById(R.id.txt_MauSac);
        KichThuoc = findViewById(R.id.txt_KichThuoc);
        Price = findViewById(R.id.txt_giaban);
        SoLuong = findViewById(R.id.txt_soluong);
        NextProduct = findViewById(R.id.image_next);
        //
        backbtn = findViewById(R.id.backIcon);
        //
        Subtotal = findViewById(R.id.subtotal_text);
        Delivery = findViewById(R.id.delivery_text);
        CheckOut = findViewById(R.id.btn_checkout);
        ClickGiamGia = findViewById(R.id.click_giamgia);
        ClickAddress = findViewById(R.id.click_address);
        ClickPayment = findViewById(R.id.click_payment);
        ClickPayment = findViewById(R.id.click_payment);
        //
        Ten = findViewById(R.id.txt_Ten);
        SDT = findViewById(R.id.txt_SDT);
        Duong = findViewById(R.id.txt_PX);
        DC = findViewById(R.id.txt_DC);

        if(MaDC == null){
            HaveDC.setVisibility(View.GONE);
        }
        else{
            DontHaveDC.setVisibility(View.GONE);
            GetSetDataDC();
        }
        if(myList != null){
            if(myList[1] == null) NextProduct.setVisibility(View.INVISIBLE);
        }



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
                t.putExtra("ListMaGH", myList);
                if(MaDC != null){
                    t.putExtra("MaDC", MaDC );
                }
                startActivity(t);
            }
        });
        ClickAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BuyNow.this, SaveAddress.class);
                t.putExtra("ListMaGH", myList);
                if(MaGG != null){
                    t.putExtra("MaGG", MaGG );
                }
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
    private void GetSetDataDC(){
        db.collection("DIACHI")
                .whereEqualTo("MaDC", MaDC)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String MaDC = doc.getString("MaDC");
                                String Name = doc.getString("TenNguoiMua");
                                String SDT = doc.getString("SoDT");
                                String DiaChi = doc.getString("DiaChi");
                                String DCDetails = doc.getString("PhuongXa") + ", "
                                        + doc.getString("QuanHuyen") + ", "
                                        + doc.getString("TinhThanhPho");
                                list.add(new Address(MaDC, Name, SDT, DiaChi, DCDetails, false));
                            }
                            Ten.setText(list.get(0).getName());
                            SDT.setText(list.get(0).getSdt());
                            Duong.setText(list.get(0).getPhuongXa());
                            DC.setText(list.get(0).getDiaChi());
                        }
                    }
                });
    }
}