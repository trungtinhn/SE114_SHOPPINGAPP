package com.example.shoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_inventory_Adapter extends RecyclerView.Adapter<My_inventory_Adapter.ProductsViewHolder> {
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item,parent,false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        product_object product = arrayList.get(position);
        if (product==null)
            return;
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.ava.setImageResource(product.getAvatar());
        holder.ware.setText(String.valueOf(product.getWarehouse()));
        holder.love.setText(String.valueOf(product.getWarehouse()));
        holder.sold.setText(String.valueOf(product.getWarehouse()));
        holder.views.setText(String.valueOf(product.getWarehouse()));

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }

    private Context context;
    private ArrayList<product_object> arrayList;

    public My_inventory_Adapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<product_object> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    public class ProductsViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name,price,ware,love,sold,views;
        private ImageView ava;
        private Button H,Edit;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            ware = itemView.findViewById(R.id.idware);
            love = itemView.findViewById(R.id.idlove);
            sold = itemView.findViewById(R.id.idsold);
            views = itemView.findViewById(R.id.idviews);
            ava = itemView.findViewById(R.id.id_avatar);
            H = itemView.findViewById(R.id.button2);
            Edit = itemView.findViewById(R.id.btn_setting);
        }
    }
}
