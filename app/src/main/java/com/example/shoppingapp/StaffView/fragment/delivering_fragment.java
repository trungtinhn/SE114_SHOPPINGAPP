package com.example.shoppingapp.StaffView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.list_item_adapter;
import com.example.shoppingapp.StaffView.item.product_object;
import com.example.shoppingapp.StaffView.item.user_object;

import java.util.ArrayList;

public class delivering_fragment extends Fragment {


    list_item_adapter myAdapter;
    ArrayList<user_object> parentItemArrayList;
    ArrayList<product_object> childItemArrayList;
    RecyclerView RVparent;
    public delivering_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_delivering_screen_oder ,container, false);
        RVparent= view.findViewById(R.id.RCV_delivering);
        setdata();
        // return view;
        return view;
    }

    private void setdata() {
        parentItemArrayList= new ArrayList<>();
        childItemArrayList= new ArrayList<>();
        parentItemArrayList.add(new user_object("Thach Sang","213123123",1500000,R.drawable.anh1));
        parentItemArrayList.add(new user_object("Thach Sang1","213123123",15000,R.drawable.anh1));
        childItemArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",213123,R.drawable.anh1,12,12,12,12,1));
        childItemArrayList.add(new product_object("T-Shirt Black Blank - VSD343545D - New Elevent",213123,R.drawable.anh1,12,12,12,12,1));

        myAdapter = new list_item_adapter(getActivity(),parentItemArrayList,childItemArrayList);
        myAdapter.setData(parentItemArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()  , RecyclerView.VERTICAL, false);
        RVparent.setLayoutManager(layoutManager);
        RVparent.setAdapter(myAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
