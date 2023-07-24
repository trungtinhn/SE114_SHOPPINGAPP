package com.example.shoppingapp.StaffView.MyProduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class My_oos_Adapter extends RecyclerView.Adapter<My_oos_Adapter.ProductsViewHolder> implements Filterable {

    private List<Product> productList;
    private List<Product> productListOld;
    private Context context;

    public My_oos_Adapter(List<Product> productList, Context context) {
        this.context = context;
        this.productList = productList;
        this.productListOld = productList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_more_product, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.soldOut.setText(String.valueOf(product.getSold()));
        holder.warehouse.setText(String.valueOf(product.getWarehouse()));
        holder.Love.setText(String.valueOf(product.getLove()));
        holder.View.setText(String.valueOf(product.getViews()));
        Picasso.get().load(R.drawable.ic_add_circle).into(holder.add);
        Picasso.get().load(product.getAvatar()).into(holder.ava);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if(search.isEmpty()){
                    productList = productListOld;
                }
                else{
                    ArrayList<Product> list = new ArrayList<>();
                    for(Product object : productListOld){
                        if(object.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(object);
                        }
                    }
                    productList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name, price;
        private ImageView ava, add;
        private Button H, Edit;
        private TextView warehouse, soldOut, Love, View;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name_add);
            price = itemView.findViewById(R.id.product_price_add);
            ava = itemView.findViewById(R.id.id_avatar_add);
            warehouse= itemView.findViewById(R.id.idware_add);
            soldOut = itemView.findViewById(R.id.idsold_add);
            Love = itemView.findViewById(R.id.idlove_add);
            View = itemView.findViewById(R.id.idviews_add);
            Edit = itemView.findViewById(R.id.btn_edit_add);
            add = itemView.findViewById(R.id.imgView_add_more);
        }
    }
}
