package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragment_detail_list_item extends Fragment {
    adapter_My_list_product myAdapter;
    TextView name;
    ArrayList<product_object> ProductArrayList;
    Button btn;
    item_object Item;
    RecyclerView RV;
    public fragment_detail_list_item(item_object item) {
        Item = item;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_my_list_item ,container, false);
        RV = view.findViewById(R.id.RCV_detail_listItems);
        btn = view.findViewById(R.id.btn_back_list_items);
        name = view.findViewById(R.id.txt_list_item_detail);
        setdata();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_List_Product_And_Item.class);
                startActivity(intent);
            }
        });
        
        // return view;
        return view;
    }

    private void setdata() {
        ProductArrayList = Item.getProduct_list();
        myAdapter = new adapter_My_list_product(this.getContext(),getActivity(),ProductArrayList);
        name.setText(Item.getName());
        myAdapter.setData(ProductArrayList);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext().getApplicationContext(), 2);
        RV.setLayoutManager(layoutManager);
        RV.setAdapter(myAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
