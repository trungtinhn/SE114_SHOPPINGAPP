package com.example.shoppingapp.StaffView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.ArrayList;

public class activity_detail_list_items extends AppCompatActivity {

    private item_object item;
    private RecyclerView RV;
    adapter_My_list_product myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_my_list_item);

        Intent intent = getIntent();
        item.setProduct_list((ArrayList<product_object>) intent.getSerializableExtra("list"));
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_my_list_item ,container, false);
        RV= view.findViewById(R.id.RCV_detail_listItems);
        setdata();
        // return view;
        return view;
    }

    private void setdata() {

        myAdapter = new adapter_My_list_product(this,this,item.getProduct_list());
        myAdapter.setData(item.getProduct_list());
        GridLayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 2);
        RV.setLayoutManager(layoutManager);
        RV.setAdapter(myAdapter);
    }

}