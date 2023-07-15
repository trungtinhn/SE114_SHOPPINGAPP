package com.example.shoppingapp.StaffView.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.StaffView.adapter.adapter_chat_board;
import com.example.shoppingapp.databinding.ActivityChatBoardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_chat_board extends AppCompatActivity {
    ActivityChatBoardBinding binding;
    FirebaseFirestore dtb;
    adapter_chat_board adapterChatBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapterChatBoard = new adapter_chat_board(this);
        binding.RCVChatBoard.setAdapter(adapterChatBoard);
        binding.RCVChatBoard.setLayoutManager(new LinearLayoutManager(this));

        dtb = FirebaseFirestore.getInstance();
        dtb.collection("NGUOIDUNG").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                adapterChatBoard.clear();
                for(DocumentSnapshot d : task.getResult()){
                    User user = d.toObject(User.class);
                    adapterChatBoard.add(user);
                }
            }
        });

//        dtb = FirebaseDatabase.getInstance().getReference("Users");
//        dtb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                adapterChatBoard.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String uid = dataSnapshot.getKey();
//                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
//                        User user = dataSnapshot.child(uid).getValue(User.class);
//                        adapterChatBoard.add(user);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

//    dtb = FirebaseFirestore.getInstance();
//        dtb.collection("NGUOIDUNG").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            adapterChatBoard.clear();
//            for(DocumentSnapshot d : task.getResult()){
//                User user = d.toObject(User.class);
//                adapterChatBoard.add(user);
//            }
//        }
//    });
}