
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

import com.example.shoppingapp.StaffView.adapter.list_item_adapter;
import com.example.shoppingapp.StaffView.item.product_object;
import com.example.shoppingapp.StaffView.item.user_object;


import java.util.ArrayList;

public class delivered_fragment extends Fragment {
    list_item_adapter myAdapter;
    ArrayList<user_object> parentItemArrayList;
    ArrayList<product_object> childItemArrayList;
    RecyclerView RVparent;
    public delivered_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_delivered_screen_oder ,container, false);
        RVparent= view.findViewById(R.id.RCV_delivered);
        // return view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
