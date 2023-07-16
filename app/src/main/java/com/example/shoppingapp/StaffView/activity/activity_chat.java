package com.example.shoppingapp.StaffView.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shoppingapp.StaffView.adapter.adapter_message;
import com.example.shoppingapp.StaffView.item.message_object;
import com.example.shoppingapp.databinding.ActivityMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class activity_chat extends AppCompatActivity {
    ActivityMessageBinding binding;
    String receiverID;
    DatabaseReference dtbSender, dtbReceiver;
    String senderRoom, receiveRoom;
    adapter_message adapterMessage;
    // delete when change adapter message
    ArrayList<message_object> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        receiverID = getIntent().getStringExtra("id");

        senderRoom = FirebaseAuth.getInstance().getUid() + receiverID;
        receiveRoom = receiverID + FirebaseAuth.getInstance().getUid();

        adapterMessage = new adapter_message(this, arrayList);
        binding.rcvMessage.setAdapter(adapterMessage);
        binding.rcvMessage.setLayoutManager(new LinearLayoutManager(this));

        dtbSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        dtbReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiveRoom);
        dtbSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapterMessage.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    message_object messageObject = dataSnapshot.getValue(message_object.class);
                    adapterMessage.add(messageObject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.editMessage.getText().toString();
                if(message.trim().length()>0){
                    sendMessage(message);
                }
            }
        });
    }

    private void sendMessage(String message) {
        String messageID = UUID.randomUUID().toString();
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        message_object messageObject = new message_object(message,FirebaseAuth.getInstance().getUid(), date.getTime(),currentTime);

        adapterMessage.add(messageObject);
        dtbSender.child(messageID)
                .setValue(messageObject);
        dtbReceiver.child(messageID)
                .setValue(messageObject);

    }
}