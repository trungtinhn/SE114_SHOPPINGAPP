package com.example.shoppingapp.customerview.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.ItemOrder;
import com.example.shoppingapp.customerview.product.customer_interface.IClickItemProductListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> mProducts;
    private IClickItemProductListener iClickItemProductListener;

    public ProductAdapter() {

    }

    public void setData(List<Product> list, IClickItemProductListener listener)
    {
        this.mProducts = list;
        this.iClickItemProductListener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProducts.get(position);
        if(product == null) return;

        //holder.imgProduct.setImageResource(product.getResouceId());
        Picasso.get().load(product.getResouceId()).into(holder.imgProduct);
        holder.txtNameProduct.setText(product.getName());

        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemProductListener.onClickItemProduct(product);
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

    public void setItemOrderList(List<ItemOrder> itemOrderList) {
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtNameProduct;
        private LinearLayout layoutProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            layoutProduct = itemView.findViewById(R.id.layoutProduct);
        }
    }
}
