package com.example.shoppingapp.customerview.activity;

import androidx.annotation.NonNull;
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
import com.example.shoppingapp.customerview.shoppingcart.Address;
import com.example.shoppingapp.customerview.shoppingcart.AddressAdapter;
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
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class SaveAddress extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<Address> addressList;
    AddressAdapter addressAdapter;
    RecyclerView recyclerView;
    ImageView backIcon ;
    LinearLayout buttonadd;
    TextView btn_done;
    String MaGG;
    String MaDC;
    String[] myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_address);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringArrayExtra("ListMaGH")!=null){
                myList = new String[intent.getStringArrayExtra("ListMaGH").length];
                myList = intent.getStringArrayExtra("ListMaGH");
            }
            if(intent.getStringExtra("MaGG") != null){
                MaGG = intent.getStringExtra("MaGG");
            }
        }
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rcyAddress);
        backIcon = findViewById(R.id.backIcon);
        buttonadd = findViewById(R.id.btn_add_address);
        btn_done = findViewById(R.id.btn_save);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(this.getApplicationContext());

        getDataAddress();

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SaveAddress.this, DeliveryAddress.class);
                t.putExtra("ListMaGH", myList);
                if(MaGG!=null){
                    t.putExtra("MaGG", MaGG);
                }
                startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SaveAddress.this, BuyNow.class);
                t.putExtra("ListMaGH", myList);
                if(MaGG!=null){
                    t.putExtra("MaGG", MaGG);
                }
                startActivity(t);
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < addressList.size(); i++){
                    if(addressList.get(i).getCheck()){
                        MaDC = addressList.get(i).getMaDC();
                    }
                }
                Intent t = new Intent(SaveAddress.this, BuyNow.class);
                t.putExtra("ListMaGH", myList);
                if(MaGG!=null){
                    t.putExtra("MaGG", MaGG);
                }
                if(MaDC!=null){
                    t.putExtra("MaDC", MaDC );
                    startActivity(t);
                }
                else{
                    Toast.makeText(SaveAddress.this, "Please choose One Address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getDataAddress(){
        db.collection("DIACHI")
                .whereEqualTo("MaND", auth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        addressList = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            String MaDC = doc.getString("MaDC");
                            String Name = doc.getString("TenNguoiMua");
                            String SDT = doc.getString("SoDT");
                            String DiaChi = doc.getString("DiaChi");
                            String DCDetails = doc.getString("PhuongXa") + ", "
                                    + doc.getString("QuanHuyen") + ", "
                                    + doc.getString("TinhThanhPho");
                            addressList.add(new Address(MaDC,Name,SDT,DiaChi,DCDetails,false));
                        }
                        addressAdapter.setData(addressList);
                        addressAdapter.setCheckClick(new AddressAdapter.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChange(int position) {
                                for(int i = 0; i < addressList.size(); i++){
                                    addressList.get(i).setCheck(false);
                                }
                                Address address = addressList.get(position);
                                addressList.get(position).setCheck(!address.getCheck());
                                addressAdapter.setData(addressList);
                            }
                        });
                        addressAdapter.setDeleteClick(new AddressAdapter.DeleteClick() {
                            @Override
                            public void deleteClickOn(int position) {
                                Address address = addressList.get(position);
                                DeleteAddress(address);
                            }
                        });
                        recyclerView.setAdapter(addressAdapter);
                    }
                });
    }
    private void DeleteAddress(Address address){
        DocumentReference docRef = db.collection("DIACHI").document(address.getMaDC());
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
    }
}