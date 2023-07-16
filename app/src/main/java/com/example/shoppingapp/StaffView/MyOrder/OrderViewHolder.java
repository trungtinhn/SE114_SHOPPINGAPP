package com.example.shoppingapp.StaffView.MyOrder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    private TextView itemNameTextView;
    private TextView itemPriceTextView;
    private TextView itemQuantityTextView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

    }

    public void bind(ItemOrder itemOrder) {
    }
}
