package com.example.shoppingapp.StaffView.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.databinding.ActivityMessageBinding;

public class activity_chat extends AppCompatActivity {
    ActivityMessageBinding binding;
    String receiverID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}