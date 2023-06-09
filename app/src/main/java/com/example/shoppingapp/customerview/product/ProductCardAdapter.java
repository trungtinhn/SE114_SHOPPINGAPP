package com.example.shoppingapp.customerview.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductTrendingListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductCardViewHolder>{

    List<ProductCard> mProductCard;
    private IClickItemProductTrendingListener listener;

    public void setData(List<ProductCard> list, IClickItemProductTrendingListener listener)
    {
        this.mProductCard = list;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_card, parent,false);

        return new ProductCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        ProductCard productCard = mProductCard.get(position);

        if(productCard == null) return;

        Picasso.get().load(productCard.getImageResouce()).into(holder.imgProductCard);
        holder.txtNameProductCard.setText(productCard.getNameProduct());
        holder.txtPriceProductCard.setText(String.valueOf(productCard.getPriceProduct()));
    }

    @Override
    public int getItemCount() {
        if (mProductCard != null) return mProductCard.size();
        return 0;
    }

    public class ProductCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProductCard;
        private TextView txtNameProductCard, txtPriceProductCard;

        public ProductCardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductCard = itemView.findViewById(R.id.imgTrendingCard);
            txtNameProductCard = itemView.findViewById(R.id.txtNameTrendingCard);
            txtPriceProductCard = itemView.findViewById(R.id.txtPriceTrendingCard);
        }
    }
}
