package com.example.shoppingapp.customerview.fragment.My_Order_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.Adapter.CheckProductAdapter_customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class delivered_fragment_Customer extends Fragment {


    private List<Order> orderList;
    private CheckProductAdapter_customer orderAdapter;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    public static delivered_fragment_Customer newInstance(String param1, String param2) {
        delivered_fragment_Customer fragment = new delivered_fragment_Customer();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivered__customer, container, false);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        RecyclerView recyclerView = view.findViewById(R.id.RCV_delivered_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Truy van
        // Truy vấn collection "DONHANG"
        CollectionReference donHangRef = firebaseFirestore.collection("DONHANG");
        donHangRef.whereEqualTo("TrangThai", "Delivered")
                .whereEqualTo("MaND", firebaseUser.getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    // Xử lý kết quả truy vấn
                    orderList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Đọc dữ liệu từ documentSnapshot
                        Order order = documentSnapshot.toObject(Order.class);
                        orderList.add(order);
                    }

                    // Khởi tạo adapter và gán nó cho RecyclerView
                    orderAdapter = new CheckProductAdapter_customer(orderList);
                    orderAdapter.refresh();
                    recyclerView.setAdapter(orderAdapter);
                });

        return view;
    }
}