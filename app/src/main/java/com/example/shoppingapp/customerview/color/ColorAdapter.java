package com.example.shoppingapp.customerview.color;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.activity.DetailProductActivity;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private List<Colors> mColor;
    DetailProductActivity detailProductActivity;

    private int selectedItem = -1;

    public void setData(List<Colors> list, DetailProductActivity detailProductActivity){
        this.mColor = list;
        this.detailProductActivity = detailProductActivity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {

        Colors color = mColor.get(position);
        if (color == null) return;


        holder.tenMau.setText(color.getTenMau());
        int mauSac = Color.parseColor(color.getMaMau());
        holder.maMau.setBackgroundColor(mauSac);
        boolean isSelected = (selectedItem == position);
        if (isSelected) {
            holder.layoutItemColor.setBackgroundResource(R.drawable.selected_item_background);
        } else {
            holder.layoutItemColor.setBackgroundResource(R.drawable.default_item_background);
        }

        holder.layoutItemColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    selectedItem = -1; // Bỏ chọn item nếu đã được chọn
                } else {
                    selectedItem = position; // Đánh dấu item được chọn
                    Colors color = mColor.get(position);
                    detailProductActivity.onColorClick(color.getTenMau());
                }
                notifyDataSetChanged(); // Cập nhật
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mColor != null)
        {
            return mColor.size();
        }
        return 0;
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout maMau;
        private LinearLayout layoutItemColor;
        private TextView tenMau;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            maMau = itemView.findViewById(R.id.maMau);
            tenMau = itemView.findViewById(R.id.tenMau);
            layoutItemColor = itemView.findViewById(R.id.layoutItemColor);
        }
    }
}
