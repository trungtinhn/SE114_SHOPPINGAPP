package com.example.shoppingapp.StaffView.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.adapter_admin_control;
import com.example.shoppingapp.StaffView.item.admin_object;
import com.example.shoppingapp.itf_RCV_list_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class activity_admin_control extends AppCompatActivity implements itf_RCV_list_item, Filterable {

    RecyclerView RCV;
    Button btn_add;
    ArrayList<admin_object> AdminList;
    adapter_admin_control adapterAdmin;
    FirebaseFirestore db;
    StorageReference storageReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_admin_control);

        //setupAdminList();

        Intent intent = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        RCV = findViewById(R.id.RCV_admin);
        btn_add = findViewById(R.id.btn_add);

        RCV.setHasFixedSize(true);
        RCV.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();

        AdminList = new ArrayList<>();
        adapterAdmin = new adapter_admin_control(this,AdminList,this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        EventInitListener();
        RCV.setAdapter(adapterAdmin);


    }

    private void EventInitListener() {
        progressDialog.setTitle("Loading data...");
        progressDialog.show();
        db.collection("ADMIN").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                AdminList.clear();
                for(DocumentSnapshot d : task.getResult())
                {
                    admin_object object = d.toObject(admin_object.class);
                    object.setKey(d.getId());
                    AdminList.add(object);

                    adapterAdmin.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(activity_admin_control.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(activity_admin_control.this, activity_admin_detail.class);
        admin_object object = AdminList.get(position);
        intent.putExtra("Data", object);
        intent.putExtra("Position", position);
        Bundle args = new Bundle();
        args.putSerializable("ArrayList",(Serializable) AdminList);
        intent.putExtra("Bundle",args);
        startActivity(intent);
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
