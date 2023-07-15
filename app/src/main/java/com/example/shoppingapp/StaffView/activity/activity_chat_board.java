package com.example.shoppingapp.StaffView.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.databinding.ActivityChatBoardBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_chat_board extends AppCompatActivity {
    ActivityChatBoardBinding binding;
    DatabaseReference dtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dtb = FirebaseDatabase.getInstance().getReference("");
    }
}