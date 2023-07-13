package com.example.shoppingapp.StaffView.MyOrder.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Adapter.OrderListAdapter;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class confirm_fragment extends Fragment {

    private static final String ARG_CUSTOMER_ID = "ID";
    private String mCustomerId;

    private RecyclerView mOrderListRecyclerView;
    private OrderListAdapter mOrderListAdapter;

    public static confirm_fragment newInstance(String customerId) {
        confirm_fragment fragment = new confirm_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CUSTOMER_ID, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomerId = getArguments().getString(ARG_CUSTOMER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_item_for_screen_oder, container, false);

        TextView customerNameTextView = rootView.findViewById(R.id.tv_ordername);
        TextView customerAddressTextView = rootView.findViewById(R.id.tv_orderID);
        ImageView customerImageView = rootView.findViewById(R.id.img_avatar);

        // Truy xuất thông tin khách hàng từ Firebase database và hiển thị trên giao diện
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference customerRef = db.collection("DONHANG");
        customerRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // Xử lý dữ liệu cho từng tài liệu tại đây
                    if (queryDocumentSnapshots.size() > 0) {
                        documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        Order customer = documentSnapshot.toObject(Order.class);
                        customerNameTextView.setText(customer.getHoTen());
                        customerAddressTextView.setText(customer.getDiaChi());
                        // Load hình từ URL sử dụng Glide hoặc Picasso
                    }
                    String documentId = documentSnapshot.getId(); // Lấy ID của tài liệu
                    Map<String, Object> data = documentSnapshot.getData(); // Lấy toàn bộ dữ liệu của tài liệu dưới dạng Map

                    // Ví dụ: Nếu bạn muốn lấy dữ liệu của một trường cụ thể, bạn có thể làm như sau:
                    Object fieldValue = documentSnapshot.get("ID"); // Ở đây 'fieldName' là tên của trường mà bạn muốn lấy dữ liệu
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OrderFragment", "Failed to read customer data", e);
            }
        });

        mOrderListRecyclerView = rootView.findViewById(R.id.RCVcard_view);
        mOrderListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mOrderListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mOrderListAdapter = new OrderListAdapter();
        mOrderListRecyclerView.setAdapter(mOrderListAdapter);

        // Truy xuất danh sách các sản phẩm củaơn hàng từ Firebase database và hiển thị trên giao diện
        CollectionReference orderRef = db.collection("DONHANG");  // Thay "orders" thành "DONHANG"

        orderRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ItemOrder> orderList = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    ItemOrder order = snapshot.toObject(ItemOrder.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                    // Gán dữ liệu danh sách đơn hàng cho adapter
                    mOrderListAdapter.setOrderList(orderList);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("OrderFragment", "Failed to read order data", e);
                }
            });

        return rootView;
        }
    }
