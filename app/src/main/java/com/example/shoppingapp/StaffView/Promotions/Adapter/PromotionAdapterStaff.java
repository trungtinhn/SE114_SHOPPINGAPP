package com.example.shoppingapp.StaffView.Promotions.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Promotions.PromotionStaff;

import java.util.List;

public class PromotionAdapterStaff extends RecyclerView.Adapter<PromotionAdapterStaff.PromotionViewHolder> {
    private List<PromotionStaff> promotionList;

    public PromotionAdapterStaff(List<PromotionStaff> promotionList) {
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        PromotionStaff promotion = promotionList.get(position);
        holder.titleTextView.setText(promotion.getChiTietKM());
        holder.descriptionTextView.setText(promotion.getTenKM());
        // Các trường thông tin khác của khuyến mãi có thể được hiển thị tại đây.
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    static class PromotionViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;

        PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.chitietKhuyenmai);
            descriptionTextView = itemView.findViewById(R.id.tenKhuyenmai);
        }
    }
}
