package com.example.shoppingapp.customerview.fragment.My_Order_fragment.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Adapter.ProductAdapter;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.example.shoppingapp.customerview.activity.activity_details_order_customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCancelAdapter_Customer extends RecyclerView.Adapter<OrderCancelAdapter_Customer.ViewHolder> {
    private List<Order> orderList;
    private String userID;
    private User user;

    public OrderCancelAdapter_Customer(List<Order> orderList) {
        this.orderList = orderList;
    }
    public OrderCancelAdapter_Customer(List<Order> orderList, String userID) {
        this.orderList = orderList;
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_for_screen_oder_cancel_customer, parent, false);
        OrderCancelAdapter_Customer.ViewHolder viewHolder = new OrderCancelAdapter_Customer.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        int receive = position;

        AtomicInteger totalMoney = new AtomicInteger(0);
        userID = FirebaseAuth.getInstance().getUid();
        holder.orderIdTextView.setText(order.getMaDH());
        holder.customerNameTextView.setText(order.getTenNguoiMua());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerViewProducts.setLayoutManager(layoutManager);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDH = orderList.get(receive).getMaDH();
                // Chuyển sang activity mới
                Intent intent = new Intent(v.getContext(), activity_details_order_customer.class);
                intent.putExtra("MaDH", maDH);
                v.getContext().startActivity(intent);
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDH = orderList.get(receive).getMaDH();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference washingtonRef = db.collection("DONHANG").document(maDH);

                washingtonRef
                        .update("TrangThai", "Confirm")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });


        // Truy vấn Firebase để lấy AnhDaiDien dựa trên maND
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        CollectionReference usersRef = db.collection("NGUOIDUNG");
        usersRef.document(firebaseUser.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String anhDaiDien = documentSnapshot.getString("avatar");
                        Picasso.get().load(anhDaiDien).into(holder.img_avatar);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });

        FirebaseFirestore db_con = FirebaseFirestore.getInstance();
        CollectionReference dathangRef = db_con.collection("DATHANG");
        dathangRef.whereEqualTo("MaDH", order.getMaDH())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ItemOrder> itemOrderList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String maSP = document.getString("MaSP");

                        String mauSac = document.getString("MauSac");
                        String size = document.getString("Size");

                        int number = document.getLong("SoLuong") != null ? Math.toIntExact(document.getLong("SoLuong")) : 0;

                        // Truy vấn Firebase để lấy thông tin sản phẩm từ MaSP
                        CollectionReference sanphamRef = db.collection("SANPHAM");
                        sanphamRef.document(maSP).get()
                                .addOnSuccessListener(sanphamDocument -> {
                                    if (sanphamDocument.exists()) {
                                        String tenSP = sanphamDocument.getString("TenSP");
                                        String hinhAnhSP = null;
                                        List<String> hinhAnhSPList = (List<String>) sanphamDocument.get("HinhAnhSP");
                                        if (hinhAnhSPList != null && !hinhAnhSPList.isEmpty()) {
                                            hinhAnhSP = hinhAnhSPList.get(0); // Lấy phần tử đầu tiên trong mảng
                                        }
                                        else {

                                            // Xử lý khi giá trị `HinhAnhSP` là null hoặc mảng rỗng
                                        }
                                        Long giaSPLong = sanphamDocument.getLong("GiaSP");
                                        int GiaSP = giaSPLong != null ? Math.toIntExact(giaSPLong) : 0;

                                        ItemOrder itemOrder = new ItemOrder(hinhAnhSP, tenSP, maSP, GiaSP, number, mauSac, size);
                                        itemOrderList.add(itemOrder);
                                        // Cập nhật tổng tiền
                                        int totalPrice = GiaSP * number;
                                        totalMoney.addAndGet(totalPrice);
                                        holder.total.setText(formatCurrency(totalMoney.get()));
                                        // Đặt giá trị tổng tiền vào TextView

                                        // Tạo mới ProductAdapter và gán nó cho recyclerViewProducts
                                        holder.productAdapter = new ProductAdapter(itemOrderList);
                                        holder.recyclerViewProducts.setAdapter(holder.productAdapter);

                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Xử lý khi truy vấn thất bại
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });
    }
    public void refresh() {
        notifyDataSetChanged();
    }
    private String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(amount);
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView customerNameTextView;
        private ImageView img_avatar;
        private RecyclerView recyclerViewProducts;
        private ProductAdapter productAdapter;

        private TextView total;
        private Button button;
        private Button cancel;
        public ViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tv_orderID_customer);
            customerNameTextView = itemView.findViewById(R.id.tv_ordername_customer);
            img_avatar = itemView.findViewById(R.id.img_avatar_customer);
            recyclerViewProducts = itemView.findViewById(R.id.RCVcard_view_customer);
            total = itemView.findViewById(R.id.moneytotal_customer);
            button = itemView.findViewById(R.id.btn_detail_customer);
            cancel = itemView.findViewById(R.id.confirm_customer);
        }
    }

}
