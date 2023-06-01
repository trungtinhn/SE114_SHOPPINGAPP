package com.example.shoppingapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_list_product_adapter extends RecyclerView.Adapter<My_list_product_adapter.list_product_holder> {

    private Activity activity;
    private ArrayList<product_object> arrayList;

    public My_list_product_adapter(Activity activity, ArrayList<product_object> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public My_list_product_adapter.list_product_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new My_list_product_adapter.list_product_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_product_holder holder, int position) {
        product_object product = arrayList.get(position);
        if (product==null)
            return;
        holder.name.setText(product.getName());
        holder.ava.setImageResource(product.getAvatar());
        holder.price.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }
    public class list_product_holder extends RecyclerView.ViewHolder
    {
        private TextView name,price;
        private ImageView ava;

        public list_product_holder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_product_name);
            price = (TextView) itemView.findViewById(R.id.txt_product_price);
            ava = itemView.findViewById(R.id.img_product_img);
        }
    }
    public void setData(ArrayList<product_object> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
}
