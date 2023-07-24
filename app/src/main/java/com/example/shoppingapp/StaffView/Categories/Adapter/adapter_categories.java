package com.example.shoppingapp.StaffView.Categories.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class adapter_categories extends RecyclerView.Adapter<adapter_categories.ViewHolder> implements Filterable {

    private static List<CategoryItem> categoryItemList;
    private List<CategoryItem> oldcategoryItemList;
    private static OnItemClickListener onItemClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if(search.isEmpty()){
                    categoryItemList = oldcategoryItemList;
                }
                else{
                    List<CategoryItem> list = new ArrayList<>();
                    for(CategoryItem object : oldcategoryItemList){
                        if(object.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(object);
                        }
                    }
                    categoryItemList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = categoryItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categoryItemList = (ArrayList<CategoryItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryItem categoryItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public adapter_categories(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
        this.oldcategoryItemList = categoryItemList;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            CategoryItem clickedItem = categoryItemList.get(position);
                            onItemClickListener.onItemClick(clickedItem);
                        }
                    }
                }
            });
        }
    }
}
