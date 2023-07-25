package com.example.shoppingapp.StaffView.ViewShop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;

import java.util.List;

public class list_adapter extends RecyclerView.Adapter<list_adapter.ViewHolder> {

    private List<CategoryItem> categoryItemList;
    private Context context;

    public list_adapter(List<CategoryItem> categoryItemList, Context context) {
        this.categoryItemList = categoryItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItemList.get(position);
        holder.txtName.setText(categoryItem.getName());
        holder.txtQuantity.setText(String.valueOf(categoryItem.getQuantity()));
        Glide.with(holder.itemView.getContext())
                .load(categoryItem.getImage())
                .into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgItem;
        public TextView txtName;
        public TextView txtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item_img);
            txtName = itemView.findViewById(R.id.txt_item_name);
            txtQuantity = itemView.findViewById(R.id.txt_item_quanity);
        }
    }
}
