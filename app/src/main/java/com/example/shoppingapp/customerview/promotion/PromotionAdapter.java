package com.example.shoppingapp.customerview.promotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.shoppingcart.Address;
import com.example.shoppingapp.customerview.shoppingcart.AddressAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {
    private List<Promotion> promotionsList;
    private Context context;
    private OnCheckedChangeListener checkClick;

    public void setData(List<Promotion> list){
        this.promotionsList = list;
        notifyDataSetChanged();
    }
    public void setCheckClick(OnCheckedChangeListener checkClick) {
        this.checkClick = checkClick;
    }
    public interface OnCheckedChangeListener {
        void onCheckedChange(int position);
    }
    public PromotionAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_promotion_customer, parent, false);
        return new PromotionAdapter.PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.PromotionViewHolder holder, int position) {
        Promotion promotion = promotionsList.get(position);
        if(promotion == null) return;

        Picasso.get().load(promotion.getHinhAnhKM()).into(holder.imageView);
        holder.TenKM.setText(promotion.getTenKM());
        holder.DonToiThieu.setText(String.valueOf(promotion.getDonToiThieu()));
        holder.NgayBD.setText(String.valueOf(promotion.getNgayBatDau().toString()));
        holder.NgayKT.setText(String.valueOf(promotion.getNgayKetThuc().toString()));


    }

    @Override
    public int getItemCount() {
        if(promotionsList.size() != 0)
        {
            return promotionsList.size();
        }
        return 0;
    }
    public class PromotionViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView DonToiThieu;
        TextView TenKM;
        TextView NgayBD;
        TextView NgayKT;
        CheckBox check;
        public PromotionViewHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.imagePromotion);
            DonToiThieu = view.findViewById(R.id.txtDetailsPromotion);
            TenKM = view.findViewById(R.id.txtNamePromotion);
            NgayBD = view.findViewById(R.id.txtStartPromotion);
            NgayKT = view.findViewById(R.id.txtEndPromotion);
            check = view.findViewById(R.id.Check);
        }
    }

}
