package com.example.shoppingapp.StaffView.MyOrder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapter_product_buy extends RecyclerView.Adapter<adapter_product_buy.ViewHolder> {
    private List<Product> productList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public ImageView image;

        // Các thành phần giao diện khác

        public ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name);
            image = itemView.findViewById(R.id.img_product);
            // Gán các thành phần giao diện khác
        }
    }

    public adapter_product_buy() {
        this.productList = new ArrayList<>();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productNameTextView.setText(product.getName());
        String maSP = product.getMaSP(); // Lấy MaSP từ đối tượng Product

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference datHangRef = db.collection("DATHANG");
        datHangRef.document(maSP).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String hinhAnhSP = documentSnapshot.getString("HinhAnhSP");
                        String tenSP = documentSnapshot.getString("TenSP");
                        // Thực hiện xử lý với hinhAnhSP
                        // Ví dụ: Hiển thị ảnh đại diện bằng Picasso
                        Picasso.get().load(hinhAnhSP).into(holder.image);
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                });

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
