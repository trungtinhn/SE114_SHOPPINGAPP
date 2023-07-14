package com.example.shoppingapp.StaffView.MyOrder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.StaffView.MyOrder.OrderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private Context context;
    private List<ItemOrder> itemOrderList;

    public OrderAdapter(Context context, List<ItemOrder> itemOrderList) {
        this.context = context;
        if (itemOrderList != null) {
            this.itemOrderList = itemOrderList;
        } else {
            this.itemOrderList = new ArrayList<>();
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        ItemOrder itemOrder = itemOrderList.get(position);
        holder.bind(itemOrder);
    }

    @Override
    public int getItemCount() {
        return itemOrderList.size();
    }
}
