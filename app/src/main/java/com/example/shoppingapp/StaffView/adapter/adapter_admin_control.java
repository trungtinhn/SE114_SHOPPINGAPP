package com.example.shoppingapp.StaffView.adapter;

import android.content.Context;
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

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.item.admin_object;
import com.example.shoppingapp.itf_RCV_list_item;

import java.util.ArrayList;

public class adapter_admin_control extends RecyclerView.Adapter<adapter_admin_control.list_admin_holder> implements Filterable {
    private itf_RCV_list_item itf_rcv_list_item;
    Context context;
    ArrayList<admin_object> arrayList;
    ArrayList<admin_object> arrayListOld;
    public adapter_admin_control( Context context, ArrayList<admin_object> objects, itf_RCV_list_item itf_rcv_list_item) {
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
        admin_object object = arrayList.get(position);
        if(object == null)
        {
            return;
        }
//        holder.ava.setImageResource(object.getAva());
        holder.name.setText(object.getName());
        holder.status.setText(object.getStatus());

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


    //    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View userView = convertView;
//        if (convertView == null) {
//            userView = LayoutInflater.from(context).inflate(R.layout.item_staff_admin_control,
//                    parent, false);
//        }
//
//        final admin_object user = getItem(position);
//
//        TextView name = userView.findViewById(R.id.txt_admin_name);
//        name.setText(user.getName());
//        ((TextView)userView.findViewById(R.id.txt_status)).setText(user.getStatus());
//
//        return userView;
//    }
    public static class list_admin_holder extends RecyclerView.ViewHolder{
        private ImageView ava;
        private TextView name;
        private TextView status;
        private LinearLayout layout;
        public list_admin_holder(@NonNull View itemView, itf_RCV_list_item itf_rcv_list_item) {
             super(itemView);
             ava = itemView.findViewById(R.id.img_admin);
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
    public void setData(ArrayList<admin_object> arrayList)
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
                    ArrayList<admin_object> list = new ArrayList<>();
                    for(admin_object object : arrayListOld){
                        if(object.getName().toLowerCase().contains(search.toLowerCase())){
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
                arrayList = (ArrayList<admin_object>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
