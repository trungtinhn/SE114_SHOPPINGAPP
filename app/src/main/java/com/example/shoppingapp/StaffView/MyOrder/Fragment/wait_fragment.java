package com.example.shoppingapp.StaffView.MyOrder.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Adapter.OrderAdapter;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cancel_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wait_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String currentUserId;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;


    public wait_fragment() {
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
    public static confirm_fragment newInstance(String param1, String param2) {
        confirm_fragment fragment = new confirm_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_order_confirm, container, false);
        // Kết nối tới Firestore

        RecyclerView recyclerView = view.findViewById(R.id.RCV_confirm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Kết nối tới Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Truy van
        // Truy vấn collection "DONHANG"

        CollectionReference donHangRef = db.collection("DONHANG");
        donHangRef.whereEqualTo("TrangThai", "Wait")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    // Xử lý kết quả truy vấn
                    orderList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Đọc dữ liệu từ documentSnapshot
                        Order order = documentSnapshot.toObject(Order.class);
                        orderList.add(order);
                    }

                    // Khởi tạo adapter và gán nó cho RecyclerView
                    orderAdapter = new OrderAdapter(orderList);
                    orderAdapter.refresh();
                    recyclerView.setAdapter(orderAdapter);
                });

        return view;
    }
}