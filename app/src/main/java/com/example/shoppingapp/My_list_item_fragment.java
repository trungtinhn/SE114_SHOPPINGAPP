package com.example.shoppingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_list_item_fragment extends Fragment {
    My_list_item_adapter myAdapter;
    ArrayList<product_object> ProductArrayList;
    ArrayList<item_object> ItemArrayList;
    RecyclerView RV;
    public My_list_item_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_list_item ,container, false);
        RV = view.findViewById(R.id.RCV_item_list);
        setdata();
        // return view;
        return view;
    }

    private void setdata() {

        ProductArrayList = new ArrayList<>();
        ProductArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",22212,R.drawable.anh1));
        ProductArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",32323,R.drawable.anh1));

        ItemArrayList = new ArrayList<>();
        ItemArrayList.add(new item_object("T-Shirt", R.drawable.anh1, ProductArrayList));
        ItemArrayList.add(new item_object("T-Shirt2", R.drawable.anh1, ProductArrayList));

        myAdapter = new My_list_item_adapter(getActivity(),ItemArrayList);
        myAdapter.setData(ItemArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()  , RecyclerView.VERTICAL, false);
        RV.setLayoutManager(layoutManager);
        RV.setAdapter(myAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
