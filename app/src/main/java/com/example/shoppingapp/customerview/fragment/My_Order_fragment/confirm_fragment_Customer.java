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
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.Adapter.OrderAdapter_Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class confirm_fragment_Customer extends Fragment {


    private List<Order> orderList;
    private OrderAdapter_Customer orderAdapter;
    private int confirmProductCount = 0;
    private confirm_fragment_Customer.OnProductCountChangeListener listener;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public confirm_fragment_Customer() {
        // Required empty public constructor
    }

    public static confirm_fragment_Customer newInstance(String param1, String param2) {
        confirm_fragment_Customer fragment = new confirm_fragment_Customer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void updateConfirmProductCount(int count) {
        confirmProductCount = count;
        if (listener != null) {
            listener.onProductCountChanged(confirmProductCount);
        }
    }
    // Thiết lập listener để giao tiếp với Activity
    public void setOnProductCountChangeListener(confirm_fragment_Customer.OnProductCountChangeListener listener) {
        this.listener = listener;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm__customer, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        RecyclerView recyclerView = view.findViewById(R.id.RCV_confirm_Customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Kết nối tới Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Truy van
        // Truy vấn collection "DONHANG"
        CollectionReference donHangRef = db.collection("DONHANG");
        donHangRef.whereEqualTo("TrangThai", "Confirm")
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
                    orderAdapter = new OrderAdapter_Customer(orderList);
                    orderAdapter.refresh();
                    recyclerView.setAdapter(orderAdapter);
                });

        return view;
    }
    public interface OnProductCountChangeListener {
        void onProductCountChanged(int count);
    }
}