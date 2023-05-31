package com.example.shoppingapp.customerview.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.notification.NotificationAdapter;
import com.example.shoppingapp.customerview.notification.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {


    RecyclerView rcvNotification;
    List<Notification> mNotification;
    NotificationAdapter notificationAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        mNotification = new ArrayList<>();
        mNotification.add(new Notification("Chương trình khuyến mãi", "Khuyến mãi siêu ưu đãi giảm đến 50%", R.drawable.mauaodep));
        mNotification.add(new Notification("Chương trình khuyến mãi", "Khuyến mãi siêu ưu đãi giảm đến 50%", R.drawable.mauaodep));
        mNotification.add(new Notification("Chương trình khuyến mãi", "Khuyến mãi siêu ưu đãi giảm đến 50%", R.drawable.mauaodep));
        mNotification.add(new Notification("Chương trình khuyến mãi", "Khuyến mãi siêu ưu đãi giảm đến 50%", R.drawable.mauaodep));

        notificationAdapter = new NotificationAdapter();
        notificationAdapter.setData(mNotification);

        rcvNotification = view.findViewById(R.id.rcvNotification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvNotification.setLayoutManager(linearLayoutManager);
        rcvNotification.setAdapter(notificationAdapter);



        return view;

    }
}