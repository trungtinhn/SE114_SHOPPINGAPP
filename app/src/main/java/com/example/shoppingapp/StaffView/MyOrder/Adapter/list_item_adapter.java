package com.example.shoppingapp.StaffView.MyOrder.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.user_object;
import com.example.shoppingapp.StaffView.card_view_adapter;
import com.example.shoppingapp.StaffView.product_object;

import java.util.ArrayList;
import java.util.List;

public class list_item_adapter extends RecyclerView.Adapter<list_item_adapter.userviewholder> {
    private Activity activity;

    private List<user_object> user_objectArrayList;
    private List<product_object> product_objectArrayList;

    public list_item_adapter(Activity activity, List<user_object> user_objectArrayList, List<product_object> product_objectArrayList) {
        this.activity = activity;
        this.user_objectArrayList = user_objectArrayList;
        this.product_objectArrayList = product_objectArrayList;
    }

    public list_item_adapter(List<user_object> orders) {
        this.user_objectArrayList = orders;
    }

    @NonNull
    @Override
    public userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_for_screen_oder,parent,false);

        return new userviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userviewholder holder, int position) {
        user_object user = user_objectArrayList.get(position);
        if (user==null)
            return;
        holder.user_name.setText(user.getName());
        holder.user_id.setText("ID: "+user.getID());
        holder.user_ava.setImageResource(user.getAva());
        holder.money.setText(String.valueOf(user.getMoneytotal()));
        card_view_adapter adapterMember = new card_view_adapter((ArrayList<product_object>) product_objectArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.RCVcardview_product.setLayoutManager(linearLayoutManager);
        holder.RCVcardview_product.setAdapter(adapterMember);
    }

    @Override
    public int getItemCount() {
        return user_objectArrayList.size();
    }
    public void setData(ArrayList<user_object>user_objectArrayList)
    {
        this.user_objectArrayList=user_objectArrayList;
        notifyDataSetChanged();
    }
    public class userviewholder extends RecyclerView.ViewHolder
    {

        private TextView user_name,user_id,money;
        private ImageView user_ava;
        private Button confirm,detail;
        private RecyclerView RCVcardview_product;

        public userviewholder(@NonNull View itemView) {
            super(itemView);
            user_name= itemView.findViewById(R.id.tv_ordername);
            user_id= itemView.findViewById(R.id.tv_orderID);
            money= itemView.findViewById(R.id.moneytotal);
            user_ava= itemView.findViewById(R.id.img_avatar);
            confirm= itemView.findViewById(R.id.btn_confirm);
            detail= itemView.findViewById(R.id.btn_detail);
            RCVcardview_product= itemView.findViewById(R.id.RCVcard_view);
        }
    }
}
