package com.example.shoppingapp.StaffView.Categories.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;

import java.util.List;

public class adapter_category_list extends RecyclerView.Adapter<adapter_category_list.ViewHolder> {

    private List<CategoryItem> categoryItemList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnCategoryCheckedChangeListener onCategoryCheckedChangeListener;

    private String selectedCategoryItem = null;

    public adapter_category_list(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public void setOnCategoryCheckedChangeListener(OnCategoryCheckedChangeListener listener) {
        this.onCategoryCheckedChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_picker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItemList.get(position);
        holder.txtName.setText(categoryItem.getName());
        holder.checkBox.setChecked(categoryItem.isSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảm bảo chỉ một danh mục được chọn
                for (CategoryItem item : categoryItemList) {
                    item.setSelected(false);
                }

                categoryItem.setSelected(true);
                selectedCategoryItem = categoryItem.getMaDM();

                notifyDataSetChanged();

                if (onCategoryCheckedChangeListener != null) {
                    onCategoryCheckedChangeListener.onCategoryCheckedChange(categoryItem);
                }
            }
        });
    }

    public String getSelectedCategory() {
        return selectedCategoryItem;
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public interface OnCategoryCheckedChangeListener {
        void onCategoryCheckedChange(CategoryItem categoryItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_category_name);
            checkBox = itemView.findViewById(R.id.check_category);
        }
    }
}
