package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.message.Message;
import com.example.shoppingapp.customerview.message.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMessage;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMessage = findViewById(R.id.rcvMessage);
        messageList = new ArrayList<>();
        messageList.add(new Message(1, "Em chào anhh", "11:30"));
        messageList.add(new Message(2, "Chào emmm", "11:30"));
        messageList.add(new Message(1, "Anh cóa người yêu chưa ạ!", "11:30"));
        messageList.add(new Message(2, "Chưa em nhé", "11:30"));
        messageList.add(new Message(1, "Thế làm người yêu em nhé, em thích anh quá điiiii", "11:30"));
        messageList.add(new Message(1, "<3", "11:30"));
        messageList.add(new Message(2, "Oke em oi", "11:30"));


        messageAdapter = new MessageAdapter(this.getApplicationContext());
        messageAdapter.setData(messageList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvMessage.setLayoutManager(linearLayoutManager);

        rcvMessage.setAdapter(messageAdapter);
    }
}