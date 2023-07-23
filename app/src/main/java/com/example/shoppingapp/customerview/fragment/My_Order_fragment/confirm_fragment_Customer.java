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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link confirm_fragment_Customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class confirm_fragment_Customer extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Order> orderList;
    private OrderAdapter_Customer orderAdapter;
    private int confirmProductCount = 0;
    private confirm_fragment_Customer.OnProductCountChangeListener listener;

    public confirm_fragment_Customer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cancel_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static confirm_fragment_Customer newInstance(String param1, String param2) {
        confirm_fragment_Customer fragment = new confirm_fragment_Customer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm__customer, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.RCV_confirm_Customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Kết nối tới Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Truy van
        // Truy vấn collection "DONHANG"
        CollectionReference donHangRef = db.collection("DONHANG");
        donHangRef.whereEqualTo("TrangThai", "Confirm")
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