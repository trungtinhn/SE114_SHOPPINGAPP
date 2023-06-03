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

import java.io.Reader;
import java.util.ArrayList;

public class adapter_detail_list_items extends RecyclerView.Adapter<adapter_detail_list_items.detail_list_holder> {
    private Activity activity;
    private Context context;
    private ArrayList<product_object> arrayList;
    private int resource;
    public adapter_detail_list_items(@NonNull Context context, int resource, ArrayList<product_object> arrayList)
    {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public adapter_detail_list_items.detail_list_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new adapter_detail_list_items.detail_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_detail_list_items.detail_list_holder holder, int position) {
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

    public void setData(ArrayList<product_object> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    public class detail_list_holder extends RecyclerView.ViewHolder {
        private TextView name,price;
        private ImageView ava;

        public detail_list_holder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_product_name);
            price = (TextView) itemView.findViewById(R.id.txt_product_price);
            ava = itemView.findViewById(R.id.img_product_img);
        }
    }
}
