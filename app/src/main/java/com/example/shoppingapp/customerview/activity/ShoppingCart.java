package com.example.shoppingapp.customerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.shoppingcart.ShoppingAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ShoppingCart extends AppCompatActivity {

    ////
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerViewdata;
    List<com.example.shoppingapp.customerview.shoppingcart.ShoppingCart> dataCheck;
    ShoppingAdapter shoppingAdapter;
    List<com.example.shoppingapp.customerview.shoppingcart.ShoppingCart> data;
    FirebaseFirestore db;
    ImageView backIcon;
    CheckBox checktotal;
    TextView Price, ButtonCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        dataCheck = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerViewdata = findViewById(R.id.RecyclerData);
        backIcon = findViewById(R.id.backIcon);
        checktotal = findViewById(R.id.radio_btn_choose);
        Price = findViewById(R.id.txt_tonggia);
        ButtonCheckOut = findViewById(R.id.btn_checkout);
        shoppingAdapter = new ShoppingAdapter(this.getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewdata.setLayoutManager(linearLayoutManager);
        getDataCart();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        checktotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checktotal.isChecked()){
                        for(int i = 0; i < data.size(); i++){
                            data.get(i).setCheck(true);
                        }
                        shoppingAdapter.setData(data);
                        SetToTal(data);
                    }
                    else{
                        for(int i = 0; i < data.size(); i++) {
                            data.get(i).setCheck(false);
                        }
                        shoppingAdapter.setData(data);
                        SetToTal(data);
                    }
                }
            });

        ButtonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String[] listmaGH = new String[data.size()];
                    Log.d("Errrrrrrrr", String.valueOf(listmaGH.length));
                    int j = 0;
                    for(int i = 0; i < data.size(); i++){
                        if(data.get(i).isCheck()){
                            listmaGH[j] = data.get(i).getMaGH();
                            j++;
                        }
                    }
                    if(listmaGH[0] !=null){
                        Intent t = new Intent(ShoppingCart.this, BuyNow.class);
                        t.putExtra("ListMaGH", listmaGH);
                        startActivity(t);
                    }
                }catch (Exception e){
                    Log.d("Errrrrrrrr", e.getMessage());
                }
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
    private void getDataCart() {
        //get Data
        db.collection("GIOHANG")
                .whereEqualTo("MaND",firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Error", "listen:error", e);
                            return;
                        }
                        data = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()) {
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
                            data.add(new com.example.shoppingapp.customerview.shoppingcart.ShoppingCart(MaSP, MaGH, Anh.get(0), TenSP, GiaSP, SoLuong, GiaTien, Size, MauSac, check));
                        }
                        shoppingAdapter.setData(data);
                        SetToTal(data);
                        shoppingAdapter.setOnButtonAddClickListener(new ShoppingAdapter.OnButtonAddClickListener() {
                            @Override
                            public void onButtonAddClick(int position) {

                                com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart = data.get(position);

                               AddSoLuong(shoppingCart);
                            }
                        });
                        shoppingAdapter.setOnButtonMinusClickListener(new ShoppingAdapter.OnButtonMinusClickListener() {
                            @Override
                            public void onButtonMinusClick(int position) {
                                com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart = data.get(position);
                                if(shoppingCart.getSoLuong() - 1 >= 1) {
                                    MinusSoLuong(shoppingCart);
                                }
                                if(shoppingCart.getSoLuong() - 1 == 0){
                                    DeleteCart(shoppingCart);
                                }
                            }
                        });
                        shoppingAdapter.setOnButtonDeleteClick(new ShoppingAdapter.OnButtonDeleteClick() {
                            @Override
                            public void onButtonDeleteClick(int position) {
                                com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart = data.get(position);
                                DeleteCart(shoppingCart);
                            }
                        });
                        shoppingAdapter.setOnCheckedChangeListener(new ShoppingAdapter.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChange(int position) {
                                com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart = data.get(position);
                                data.get(position).setCheck(!shoppingCart.isCheck());
                                shoppingAdapter.setData(data);
                                boolean check = true;
                                for(int i = 0; i < data.size(); i++){
                                    if(!data.get(i).isCheck())
                                        check = false;
                                }
                                checktotal.setChecked(check);
                                SetToTal(data);
                            }
                        });
                        recyclerViewdata.setAdapter(shoppingAdapter);
                    }
                });
    }
    private void AddSoLuong(com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart){
        try {
            db.collection("GIOHANG")
                    .whereEqualTo("MaGH", shoppingCart.getMaGH())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                String docId = documentSnapshot.getId();
                                Map<String, Object> newData = new HashMap<>();
                                newData.put("SoLuong", shoppingCart.getSoLuong()+1);
                                newData.put("GiaTien", (shoppingCart.getSoLuong()+1)*shoppingCart.getGiaSP());
                                DocumentReference docRef = db.collection("GIOHANG").document(docId);
                                docRef.update(newData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Access", "SoLuong updated successfully");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Error", "Error updating SoLuong", e);
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error", "Error fetching document", e);
                        }
                    });
        }catch (Exception ex){

        }

    }
    private void MinusSoLuong(com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart){
        try {
            db.collection("GIOHANG")
                    .whereEqualTo("MaGH", shoppingCart.getMaGH())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                String docId = documentSnapshot.getId();
                                Map<String, Object> newData = new HashMap<>();
                                newData.put("SoLuong", shoppingCart.getSoLuong()-1);
                                newData.put("GiaTien", (shoppingCart.getSoLuong()-1)*shoppingCart.getGiaSP());
                                DocumentReference docRef = db.collection("GIOHANG").document(docId);
                                docRef.update(newData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Access", "SoLuong updated successfully");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Error", "Error updating SoLuong", e);
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error", "Error fetching document", e);
                        }
                    });
        }catch (Exception ex){

        }

    }
    private void DeleteCart(com.example.shoppingapp.customerview.shoppingcart.ShoppingCart shoppingCart){
        try {
            DocumentReference docRef = db.collection("GIOHANG").document(shoppingCart.getMaGH());
            docRef.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Sucess", "Data deleted successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error", "Error deleting data", e);
                        }
                    });
        }catch (Exception ex){

        }

    }
    private void SetToTal(List<com.example.shoppingapp.customerview.shoppingcart.ShoppingCart> data){
        int Sum = 0;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).isCheck()){
                Sum += data.get(i).getGiaTien();
            }
        }
        Price.setText(String.valueOf(Sum));
    }
}