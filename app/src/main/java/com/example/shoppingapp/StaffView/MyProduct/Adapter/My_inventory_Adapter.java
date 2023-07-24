package com.example.shoppingapp.StaffView.MyProduct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.shoppingapp.StaffView.MyProduct.Activity.activity_edit_product;
import com.example.shoppingapp.StaffView.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class My_inventory_Adapter extends RecyclerView.Adapter<My_inventory_Adapter.ProductsViewHolder> implements Filterable {

    private List<Product> productList;
    private List<Product> productListOld;
    private Context context;
    private List<String> imageUrls = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private Uri selectedImageUri;

    public My_inventory_Adapter(List<Product> productList, Context context) {
        this.context = context;
        this.productList = productList;
        this.productListOld = productList;
    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ProductsViewHolder(view);
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.imageUrls.addAll(imageUrls);
        notifyDataSetChanged();
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
        Picasso.get().load(product.getAvatar()).into(holder.ava);
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_edit_product.class);
                intent.putExtra("MaSP", product.getMaSP());
                v.getContext().startActivity(intent);
            }
        });

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
        private ImageView ava;
        private Button H, Edit;
        private TextView warehouse, soldOut, Love, View;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            ava = itemView.findViewById(R.id.id_avatar);
            warehouse= itemView.findViewById(R.id.idware);
            soldOut = itemView.findViewById(R.id.idsold);
            Love = itemView.findViewById(R.id.idlove);
            View = itemView.findViewById(R.id.idviews);
            H = itemView.findViewById(R.id.button2);
            Edit = itemView.findViewById(R.id.button3);
        }
    }

}
