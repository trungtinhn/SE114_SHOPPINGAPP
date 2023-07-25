package com.example.shoppingapp.StaffView.ViewShop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class product_adapter extends RecyclerView.Adapter<product_adapter .ProductsViewHolder> {
    private List<Product> productList;
    private Context context;

    public product_adapter (List<Product> productList, Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        CollectionReference sanphamRef = FirebaseFirestore.getInstance().collection("SANPHAM");

        sanphamRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                productList.clear();

                for (DocumentSnapshot document : task.getResult()) {
                    // Lấy các trường dữ liệu từ document
                    // Lấy mảng địa chỉ ảnh
                    List<String> hinhAnhSPList = (List<String>) document.get("HinhAnhSP");

                    // Lấy địa chỉ ảnh đầu tiên trong mảng
                    String hinhAnhSP = hinhAnhSPList != null && !hinhAnhSPList.isEmpty() ? hinhAnhSPList.get(0) : "";
                    String tenSP = document.getString("TenSP");
                    int giaSP = document.getLong("GiaSP") != null ? document.getLong("GiaSP").intValue() : 0;

                    // Tạo đối tượng Product từ dữ liệu lấy được
                    Product product = new Product(hinhAnhSP, tenSP, giaSP);

                    // Thêm đối tượng Product vào danh sách
                    productList.add(product);
                }

                notifyDataSetChanged();
            } else {
                // Xử lý khi không thành công
                Exception exception = task.getException();
                // ...
            }
        });
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        Picasso.get().load(product.getAvatar()).into(holder.ava);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name, price;
        private ImageView ava;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_product_name);
            price = itemView.findViewById(R.id.txt_product_price);
            ava = itemView.findViewById(R.id.img_product_img);
        }
    }
}
