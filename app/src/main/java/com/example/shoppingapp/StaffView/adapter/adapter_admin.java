package com.example.shoppingapp.StaffView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.item.admin_object;

import java.util.ArrayList;

public class adapter_admin extends ArrayAdapter<admin_object> {
    Context context;

    public adapter_admin(@NonNull Context context, @NonNull ArrayList<admin_object> objects) {
        super(context, 0, objects);
        this.context = context;
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View userView = convertView;
        if (convertView == null) {
            userView = LayoutInflater.from(context).inflate(R.layout.item_staff_admin_control,
                    parent, false);
        }

        final admin_object user = getItem(position);

        TextView name = userView.findViewById(R.id.txt_admin_name);
        name.setText(user.getName());
        ((TextView)userView.findViewById(R.id.txt_status)).setText(user.getStatus());

        return userView;
    }
}
