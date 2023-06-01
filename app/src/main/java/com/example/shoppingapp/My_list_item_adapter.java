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

public class My_list_item_adapter extends RecyclerView.Adapter<My_list_item_adapter.list_item_holder> {

    Context context;
    private Activity activity;
    private ArrayList<item_object> arrayList;

    public My_list_item_adapter(Activity activity, ArrayList<item_object> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public list_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem,parent,false);
        return new list_item_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_item_holder holder, int position) {
        item_object product = arrayList.get(position);
        if (product==null)
            return;
        holder.name.setText(product.getName());
        holder.ava.setImageResource(product.getImage());
        holder.quanity.setText(String.valueOf(product.getQuanity()));
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }
    public class list_item_holder extends RecyclerView.ViewHolder
    {
        private TextView name,quanity;
        private ImageView ava;

        public list_item_holder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_item_name);
            quanity = (TextView) itemView.findViewById(R.id.txt_item_quanity);
            ava = itemView.findViewById(R.id.img_item_img);
        }
    }
    public void setData(ArrayList<item_object> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
}
