package com.example.shoppingapp.StaffView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.adapter_My_list_product;
import com.example.shoppingapp.StaffView.item.product_object;

import java.util.ArrayList;

public class fragment_My_list_product extends Fragment {
    adapter_My_list_product myAdapter;
    ArrayList<product_object> ProductArrayList;
    RecyclerView RV;
    public fragment_My_list_product() {
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
        View view = inflater.inflate(R.layout.fragment_my_list_product ,container, false);
        RV= view.findViewById(R.id.RCV_product_list);
        setdata();
        // return view;
        return view;
    }

    private void setdata() {

        ProductArrayList= new ArrayList<>();

        ProductArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",2,R.drawable.anh1));
        ProductArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",3,R.drawable.anh1));

        myAdapter = new adapter_My_list_product(this.getContext(),getActivity(),ProductArrayList);
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
