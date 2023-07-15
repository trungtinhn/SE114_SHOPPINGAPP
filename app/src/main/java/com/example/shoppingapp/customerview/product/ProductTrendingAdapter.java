package com.example.shoppingapp.customerview.product;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.categories.CategoriesAdapter;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTrendingAdapter extends  RecyclerView.Adapter<ProductTrendingAdapter.ProductViewHolder>{

    private List<ProductTrending> mProducts;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onButtonItemClick(int position);
    }
    public void setData(List<ProductTrending> list)
    {
        this.mProducts = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductTrending product = mProducts.get(position);
        if(product == null) return;
        //holder.imgProduct.setImageResource(product.getResouceId());
        Picasso.get().load(product.getResouceId()).into(holder.imgProduct);
        holder.txtNameProduct.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPriceProduct()));
        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick != null){
                    onItemClick.onButtonItemClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mProducts != null){
            return mProducts.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtNameProduct;
        private TextView price;
        private RelativeLayout layoutProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgTrendingCard);
            txtNameProduct = itemView.findViewById(R.id.txtNameTrendingCard);
            price = itemView.findViewById(R.id.txtPriceTrendingCard);
            layoutProduct = itemView.findViewById(R.id.layout);

        }

    }
}

