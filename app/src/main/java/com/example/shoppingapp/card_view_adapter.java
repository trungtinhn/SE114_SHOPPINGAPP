package com.example.shoppingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class card_view_adapter extends RecyclerView.Adapter<card_view_adapter.ProductsViewHolder_oder> {

    private ArrayList<product_object> arrayList;


    public ProductsViewHolder_oder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_on_list_item,parent,false);
        return new ProductsViewHolder_oder(view);
    }

    public card_view_adapter(ArrayList<product_object> arrayList) {

        this.arrayList = arrayList;

    }

    @Override
    public void onBindViewHolder(@NonNull card_view_adapter.ProductsViewHolder_oder holder, int position) {
        product_object product = arrayList.get(position);
        if (product==null)
            return;
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.ava.setImageResource(product.getAvatar());
        holder.count.setText(String.valueOf(product.getCount()));
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
    public class ProductsViewHolder_oder extends RecyclerView.ViewHolder
    {
        private TextView name,price,count;
        private ImageView ava;

        public ProductsViewHolder_oder(@NonNull View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.product_name_cardview_on_order);
            price =(TextView) itemView.findViewById(R.id.product_price_cardview_on_order);
            ava = itemView.findViewById(R.id.id_avatar);
            count =(TextView) itemView.findViewById(R.id.count_cardview_on_order);
        }
    }
}
