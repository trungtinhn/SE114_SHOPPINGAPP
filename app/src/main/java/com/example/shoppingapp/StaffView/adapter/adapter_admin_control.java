package com.example.shoppingapp.StaffView.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.itf_RCV_list_item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_admin_control extends RecyclerView.Adapter<adapter_admin_control.list_admin_holder> implements Filterable {
    private itf_RCV_list_item itf_rcv_list_item;
    Context context;
    ArrayList<User> arrayList;
    ArrayList<User> arrayListOld;
    public adapter_admin_control( Context context, ArrayList<User> objects, itf_RCV_list_item itf_rcv_list_item) {
        this.arrayList = objects;
        this.context = context;
        this.arrayListOld = objects;
        this.itf_rcv_list_item = itf_rcv_list_item;
    }

    @NonNull
    @Override
    public list_admin_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_admin_control,
                parent,false);
        return new adapter_admin_control.list_admin_holder(view, itf_rcv_list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull list_admin_holder holder, int position) {
        User object = arrayList.get(position);
        if(object == null)
        {
            return;
        }
        String uri=object.getAvatar();

        Picasso.get().load(uri).into(holder.ava);
        if(object.getStatus().equals("Online"))
        {
            holder.status.setText(object.getStatus());
            holder.status.setTextColor(Color.GREEN);
        }
        else
        {
            holder.status.setText(object.getStatus());
        }
        holder.name.setText(object.getFullName());
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }


    public static class list_admin_holder extends RecyclerView.ViewHolder{
        private ImageView ava;
        private TextView name;
        private TextView status;
        private LinearLayout layout;
        public list_admin_holder(@NonNull View itemView, itf_RCV_list_item itf_rcv_list_item) {
             super(itemView);
             ava = itemView.findViewById(R.id.img_staff);
             name = itemView.findViewById(R.id.txt_admin_name);
             status = itemView.findViewById(R.id.txt_status);
             layout = itemView.findViewById(R.id.layout_admin);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(itf_rcv_list_item != null){
                         int pos = getAdapterPosition();
                         if(pos != RecyclerView.NO_POSITION){
                             itf_rcv_list_item.onClick(pos);
                         }
                     }
                 }
             });
        }
    }
    public void setData(ArrayList<User> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if(search.isEmpty()){
                    arrayList = arrayListOld;
                }
                else{
                    ArrayList<User> list = new ArrayList<>();
                    for(User object : arrayListOld){
                        if(object.getFullName().toLowerCase().contains(search.toLowerCase())){
                            list.add(object);
                        }
                    }
                    arrayList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
