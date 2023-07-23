package com.example.shoppingapp.customerview.fragment.My_Order_fragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter_customer extends  RecyclerView.Adapter<ProductAdapter_customer.ViewHolder>{


    private List<ItemOrder> itemOrderList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView number;
        public TextView number_of;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name_customer);
            image = itemView.findViewById(R.id.img_product_customer);
            number = itemView.findViewById(R.id.product_price_customer);
            number_of = itemView.findViewById(R.id.number_of_product_customer);
        }
    }
    public ProductAdapter_customer(List<ItemOrder> itemOrderList) {
        this.itemOrderList = itemOrderList;
    }
    public ProductAdapter_customer() {
        this.itemOrderList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ProductAdapter_customer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_customer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_customer.ViewHolder holder, int position) {
        ItemOrder itemOrder = itemOrderList.get(position);

        // Hiển thị thông tin sản phẩm
        holder.productNameTextView.setText(itemOrder.getTenSP());
        holder.number.setText(String.valueOf(itemOrder.getGiaSP()));
        holder.number_of.setText(String.valueOf(itemOrder.getSoLuong()));
        if(itemOrder.getHinhAnhSP() == null)
        {
            Picasso.get().load(R.drawable.anh1).into(holder.image);
        }
        else
        {
            Picasso.get().load(itemOrder.getHinhAnhSP()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return itemOrderList.size();
    }
    private String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(amount);
    }
}



