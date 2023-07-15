package com.example.shoppingapp.StaffView.MyOrder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.Locale;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    private TextView itemNameTextView;
    private TextView itemPriceTextView;
    private TextView itemQuantityTextView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        itemNameTextView = itemView.findViewById(R.id.product_name);
        itemPriceTextView = itemView.findViewById(R.id.product_price);
        itemQuantityTextView = itemView.findViewById(R.id.number_of_product);
    }

    public void bind(ItemOrder order) {
        itemNameTextView.setText(order.getTenSP());
        itemPriceTextView.setText(String.format(Locale.getDefault(), "%.0f đ", order.getGia()));
        itemQuantityTextView.setText(String.format(Locale.getDefault(), "x%d", order.getSoLuong()));
    }
}
