package com.example.shoppingapp.customerview.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.customerview.product.Product;

import java.util.List;
public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingCartViewHolder>{
    private List<ShoppingCart> data;
    private IClickItemProductListener iClickItemShoppingCartListener;

    public void setData(List<ShoppingCart> list, IClickItemProductListener listener)
    {
        this.data = list;
        this.iClickItemShoppingCartListener= listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcart, parent, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        ShoppingCart cart = data.get(position);
        if(cart == null) return;

        holder.check.setChecked(cart.isCheck());
        holder.imagesp.setImageResource(cart.getDataImage());
        holder.tensp.setText(cart.getTenSanPham());
        holder.soluong.setText(cart.getSoLuong());
        holder.tendanhmuc.setText(cart.getTenDanhMuc());
        holder.giaban.setText(cart.getGia());

//        holder.btn_delete.setOnClickListener(View.OnClickListener(){
//
//        });

    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagesp, btn_minus, btn_add, btn_delete;
        private RadioButton check;
        private TextView tensp, tendanhmuc, giaban, soluong;

        public ShoppingCartViewHolder(@NonNull View itemView){
            super(itemView);
            imagesp = itemView.findViewById(R.id.image_product);
            check = itemView.findViewById(R.id.check_button);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            tensp = itemView.findViewById(R.id.txt_tensanpham);
            tendanhmuc = itemView.findViewById(R.id.txt_tendanhmuc);
            giaban = itemView.findViewById(R.id.txt_giaban);
            soluong = itemView.findViewById(R.id.txt_soluong);
        }
    }
}
