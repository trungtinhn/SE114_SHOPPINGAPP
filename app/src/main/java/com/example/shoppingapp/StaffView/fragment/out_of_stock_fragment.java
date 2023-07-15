package com.example.shoppingapp.StaffView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyProduct.Adapter.My_inventory_Adapter;
import com.example.shoppingapp.StaffView.item.product_object;

import java.util.ArrayList;


public class out_of_stock_fragment extends Fragment {

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView1;
    private ArrayList<product_object> arrayList= new ArrayList<>();
    private My_inventory_Adapter adapter;

    public out_of_stock_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_oos, container, false);
        recyclerView1= view.findViewById(R.id.RCV_My_oos);
        setdata();
        return view;
    }

    private void setdata() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}