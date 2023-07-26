package com.example.shoppingapp.customerview.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    List<Notification> mNotification;

    public void setData(List<Notification> mNotification){
        this.mNotification = mNotification;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = mNotification.get(position);
        if(notification == null) return;
        holder.txtNameNotification.setText(notification.getName());
        holder.txtContentNotification.setText(notification.getContent());
        holder.imgAvt.setImageResource(notification.getResourceAvt());

    }

    @Override
    public int getItemCount() {
        if (mNotification != null) return mNotification.size();
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNameNotification;
        private TextView txtContentNotification;
        private ShapeableImageView imgAvt;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameNotification = itemView.findViewById(R.id.txtNameNotification);
            txtContentNotification = itemView.findViewById(R.id.txtContentNotification);
            imgAvt = itemView.findViewById(R.id.imgAvt);

        }
    }
}
