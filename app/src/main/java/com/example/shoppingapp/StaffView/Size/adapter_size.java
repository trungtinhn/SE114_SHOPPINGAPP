package com.example.shoppingapp.StaffView.Size;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class adapter_size extends RecyclerView.Adapter<adapter_size.SizeViewHolder> {
    private List<Size> sizeList;
    private Size size;
    public List<String> getSelectedSizes() {
        List<String> selectedSizes = new ArrayList<>();
        for (Size size : sizeList) {
            if (size.isChecked()) {
                selectedSizes.add(size.getMaSize());
            }
        }
        return selectedSizes;
    }
    public adapter_size(Size size){
        this.size = size;
    }

    private OnSizeCheckedChangeListener onSizeCheckedChangeListener;

    public adapter_size(List<Size> sizeList) {
        this.sizeList = sizeList;
        this.onSizeCheckedChangeListener = onSizeCheckedChangeListener;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size_product, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        Size size = sizeList.get(position);
        holder.sizeTextView.setText(size.getSizeName());
        holder.checkBox.setChecked(size.isChecked());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            size.setChecked(isChecked);
            if (onSizeCheckedChangeListener != null) {
                onSizeCheckedChangeListener.onSizeCheckedChange(size);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public interface OnSizeCheckedChangeListener {
        void onSizeCheckedChange(Size size);
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder {
        TextView sizeTextView;
        CheckBox checkBox;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeTextView = itemView.findViewById(R.id.txt_size_name);
            checkBox = itemView.findViewById(R.id.check_size);
        }
    }
}
