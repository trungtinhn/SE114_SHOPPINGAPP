package com.example.shoppingapp.customerview.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

    private List<Categories> mCategories;
    public void setData(List<Categories> list){
        this.mCategories = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);

        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Categories categories = mCategories.get(position);
        if (categories == null) return;

        holder.txtNameCategories.setText(categories.getName());

    }

    @Override
    public int getItemCount() {
        if (mCategories != null)
        {
            return mCategories.size();
        }
        return 0;
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNameCategories;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCategories = itemView.findViewById(R.id.txtNameCategories);
        }
    }
}