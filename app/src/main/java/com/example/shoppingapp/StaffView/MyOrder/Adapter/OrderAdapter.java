package com.example.shoppingapp.StaffView.MyOrder.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Activity.activity_details_order;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> orderList;
    private String userID;
    private User user;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView customerNameTextView;
        private ImageView img_avatar;
        private RecyclerView recyclerViewProducts;
        private ProductAdapter productAdapter;

        private TextView total;
        private Button button;
        private Button confirm;
        public ViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tv_orderID);
            customerNameTextView = itemView.findViewById(R.id.tv_ordername);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            recyclerViewProducts = itemView.findViewById(R.id.RCVcard_view);
            total = itemView.findViewById(R.id.moneytotal);
            button = itemView.findViewById(R.id.btn_detail);
            confirm = itemView.findViewById(R.id.confirm);

        }
    }


    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }
    public List<Order> getOrderList() {
        return orderList;
    }
    public OrderAdapter(List<Order> orderList, String userID) {
        this.orderList = orderList;
        this.userID = userID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_for_screen_oder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orderList.get(position);
        int receive = position;
        holder.orderIdTextView.setText(order.getMaDH());
        holder.customerNameTextView.setText(order.getTenNguoiMua());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerViewProducts.setLayoutManager(layoutManager);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDH = orderList.get(receive).getMaDH();
                // Chuyển sang activity mới
                Intent intent = new Intent(v.getContext(), activity_details_order.class);
                intent.putExtra("MaDH", maDH);
                v.getContext().startActivity(intent);
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDH = orderList.get(receive).getMaDH();
                FirebaseFirestore db_confirm = FirebaseFirestore.getInstance();
                CollectionReference donHangRef = db_confirm.collection("DONHANG");
                DocumentReference docRef = donHangRef.document(maDH);

                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String trangThai = documentSnapshot.getString("TrangThai");
                        if (trangThai != null) {
                            String newTrangThai;
                            if (trangThai.equals("Confirm")) {
                                newTrangThai = "Wait";
                            } else if (trangThai.equals("Wait")) {
                                newTrangThai = "Delivering";
                            } else if (trangThai.equals("Delivering")) {
                                newTrangThai = "Delivered";
                            } else {
                                return;
                            }
                            docRef.update("TrangThai", newTrangThai)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Cập nhật thành công
                                                Toast.makeText(v.getContext(), "Trạng thái đã được cập nhật", Toast.LENGTH_SHORT).show();
                                                refresh();
                                            } else {
                                                // Cập nhật thất bại
                                                Toast.makeText(v.getContext(), "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String MaND = firebaseAuth.getUid();
                        Timestamp timestamp = Timestamp.now();
                        Map<String, Object> xacNhanDonHang = new HashMap<>();
                        xacNhanDonHang.put("MaDH", maDH);
                        xacNhanDonHang.put("MaND", MaND);
                        xacNhanDonHang.put("NgayGioBamNutConfirm", timestamp);
                        xacNhanDonHang.put("TrangThai", trangThai); // Lưu giá trị TrangThai
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference xacNhanDonHangRef = db.collection("XACNHANDONHANG");
                        if (trangThai.equals("Confirm") || trangThai.equals("Wait")) {
                            xacNhanDonHangRef.add(xacNhanDonHang)
                                    .addOnSuccessListener(documentReference1 -> {
                                        // Tạo tài liệu thành công
                                        String xacNhanDonHangId = documentReference1.getId();
                                        // Thực hiện các thao tác khác nếu cần
                                    })
                                    .addOnFailureListener(e -> {
                                        // Tạo tài liệu thất bại
                                        // Xử lý lỗi nếu cần
                                    });
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // Truy vấn Firebase để lấy AnhDaiDien dựa trên maND
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("NGUOIDUNG");
        usersRef.document(order.getMaND()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String anhDaiDien = documentSnapshot.getString("avatar");
                        Picasso.get().load(anhDaiDien).into(holder.img_avatar);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });

        CollectionReference tongtienRef = FirebaseFirestore.getInstance().collection("DONHANG");
        DocumentReference tienRef = tongtienRef.document(order.getMaDH());
        tienRef
                .addSnapshotListener((documentSnapshot,e) -> {
                    if (documentSnapshot.exists()) {
                        long tongtien = documentSnapshot.getLong("TongTien");
                        holder.total.setText(formatCurrency(Integer.parseInt(String.valueOf(tongtien))));
                    }
                });

        FirebaseFirestore db_con = FirebaseFirestore.getInstance();
        CollectionReference dathangRef = db_con.collection("DATHANG");
        dathangRef.whereEqualTo("MaDH", order.getMaDH())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ItemOrder> itemOrderList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String maSP = document.getString("MaSP");
                        int number = document.getLong("SoLuong") != null ? Math.toIntExact(document.getLong("SoLuong")) : 0;
                        String color = document.getString("MauSac");
                        String size = document.getString("Size");
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

                                        ItemOrder itemOrder = new ItemOrder(hinhAnhSP, tenSP, maSP, GiaSP, number, color, size);
                                        itemOrderList.add(itemOrder);
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

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    private String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(amount);
    }
    public void refresh() {
        notifyDataSetChanged();
    }

}
