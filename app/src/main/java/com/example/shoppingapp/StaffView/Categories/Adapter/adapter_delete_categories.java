package com.example.shoppingapp.StaffView.Categories.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class adapter_delete_categories extends RecyclerView.Adapter<adapter_delete_categories.ViewHolder> {

    private List<CategoryItem> categoryItemList;

    public List<CategoryItem> getSelectedCategories() {
        List<CategoryItem> selectedCategories = new ArrayList<>();
        for (CategoryItem categoryItem : categoryItemList) {
            if (categoryItem.isSelected()) {
                selectedCategories.add(categoryItem);
            }
        }
        return selectedCategories;
    }
    public adapter_delete_categories(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_delete, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItemList.get(position);
        holder.bind(categoryItem); // Thêm dòng này để ánh xạ dữ liệu vào ViewHolder
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
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item_img);
            txtName = itemView.findViewById(R.id.txt_item_name);
            txtQuantity = itemView.findViewById(R.id.txt_item_quanity);
            checkBox = itemView.findViewById(R.id.check);
        }

        public void bind(CategoryItem categoryItem) {
            txtName.setText(categoryItem.getName());
            txtQuantity.setText(String.valueOf(categoryItem.getQuantity()));
            checkBox.setChecked(categoryItem.isSelected());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    categoryItem.setSelected(isChecked);
                }
            });
        }
    }
}
