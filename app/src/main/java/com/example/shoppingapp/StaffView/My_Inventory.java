package com.example.shoppingapp.StaffView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.R;

import java.util.ArrayList;


public class My_Inventory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView1;
    private ArrayList<product_object> arrayList= new ArrayList<>();
    private My_inventory_Adapter adapter;

    public My_Inventory() {
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
        View view = inflater.inflate(R.layout.fragment_my__inventory, container, false);
        recyclerView1= view.findViewById(R.id.RCV_My_inventory);
        setdata();
        return view;
    }

    private void setdata() {
        arrayList= new ArrayList<>();
        arrayList.add(new product_object("T-Shirt Black Blank-VSD343545D - New Elevent",139.999,R.drawable.anh1,20,20,20,20,1));
        arrayList.add(new product_object("T-Shirt Black Blank-VSD3435231 - New Elevent",139.909,R.drawable.anh1,20,20,20,20,1));
        arrayList.add(new product_object("T-Shirt Black Blank-VSD343523d - New Elevent",139.9009,R.drawable.anh1,20,20,20,20,1));
        adapter = new My_inventory_Adapter(getActivity());
        adapter.setData(arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()  , RecyclerView.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}