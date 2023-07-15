package com.example.shoppingapp.StaffView.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.activity.activity_chat;

import java.util.ArrayList;
import java.util.List;

public class adapter_chat_board extends RecyclerView.Adapter<adapter_chat_board.MyViewHolder> {
    private Context context;
    private List<User> userList;

    public adapter_chat_board(Context context)
    {
        this.context = context;
        this.userList = new ArrayList<>();
    }

    public void add(User user){
        userList.add(user);
        notifyDataSetChanged();
    }
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
       try{
                holder.name.setText(user.getFullName());
            }
            catch (Exception e){}
       try{
                holder.email.setText(user.getEmail());
            }
            catch (Exception e){}
       try{
                holder.avt.setImageURI(Uri.parse(user.getAvatar()));
            }
            catch (Exception e){}
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, activity_chat.class);
               intent.putExtra("id",user.getMaND());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, email;
        private ImageView avt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            avt = itemView.findViewById(R.id.userImg);
        }
    }
}
