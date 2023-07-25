package com.example.shoppingapp.customerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoneActivity extends AppCompatActivity {
    TextView checkstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        checkstatus = findViewById(R.id.btn_checkstatus);

        checkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(DoneActivity.this, BottomNavigationCustomActivity.class);
                startActivity(t);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference= FirebaseFirestore.getInstance().
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
}