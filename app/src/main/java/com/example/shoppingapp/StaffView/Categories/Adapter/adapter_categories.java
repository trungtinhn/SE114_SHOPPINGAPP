package com.example.shoppingapp.StaffView.Categories.Adapter;

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

public class adapter_categories extends RecyclerView.Adapter<adapter_categories.ViewHolder> {

    private List<CategoryItem> categoryItemList;

    public adapter_categories(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
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
