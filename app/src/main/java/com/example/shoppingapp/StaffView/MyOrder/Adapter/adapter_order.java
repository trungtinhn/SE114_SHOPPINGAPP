package com.example.shoppingapp.StaffView.MyOrder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_order extends RecyclerView.Adapter<adapter_order.ViewHolder> {
    private List<Order> orderList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView customerNameTextView;
        private ImageView img_avatar;
        // Các thành phần giao diện khác

        public ViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tv_orderID);
            customerNameTextView = itemView.findViewById(R.id.tv_ordername);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            // Gán các thành phần giao diện khác
        }
    }

    public adapter_order(List<Order> orderList) {
        this.orderList = orderList;
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

        holder.orderIdTextView.setText(order.getMaND());
        holder.customerNameTextView.setText(order.getTenNguoiMua());
        // Đặt các giá trị khác cho các thành phần giao diện khác

        holder.orderIdTextView.setText(order.getMaND());
        holder.customerNameTextView.setText(order.getTenNguoiMua());

        // Truy vấn Firebase để lấy AnhDaiDien dựa trên maND
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("NGUOIDUNG");
        usersRef.document(order.getMaND()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String anhDaiDien = documentSnapshot.getString("avatar");
                        // Thực hiện xử lý với anhDaiDien
                        // Ví dụ: Hiển thị ảnh đại diện bằng Picasso
                        Picasso.get().load(anhDaiDien).into(holder.img_avatar);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });

        // Đặt các giá trị khác cho các thành phần giao diện khác
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
