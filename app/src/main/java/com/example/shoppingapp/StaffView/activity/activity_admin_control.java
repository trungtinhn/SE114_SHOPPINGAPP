package com.example.shoppingapp.StaffView.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.adapter_admin;
import com.example.shoppingapp.StaffView.item.admin_object;

public class activity_admin_control extends AppCompatActivity {
    ListView LV;
    Button btn_add;
    adapter_admin adapterAdmin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_admin_control);

        Intent intent = getIntent();
        LV = findViewById(R.id.LV_admin);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final admin_object model = adapterAdmin.getItem(position);
//                Intent intent = new Intent(SearchVehical.this, VehicleDetail.class);
//                intent.putExtra("Data", model);
//                startActivity(intent);
            }
        });

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_admin_control.this, add_admin.class));
            }
        });
    }
}
