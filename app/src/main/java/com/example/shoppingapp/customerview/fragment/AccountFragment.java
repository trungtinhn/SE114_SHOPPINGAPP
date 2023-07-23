package com.example.shoppingapp.customerview.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.activity.OrderActivity;


public class AccountFragment extends Fragment {

    private LinearLayout YourOrder;
    private LinearLayout Changepass;
    private LinearLayout Help;
    private LinearLayout Profile;
    private BottomNavigationCustomActivity bottomNavigationCustomActivity;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        YourOrder = view.findViewById(R.id.l1);
        Changepass = view.findViewById(R.id.l4);
        Help = view.findViewById(R.id.l2);
        Profile = view.findViewById(R.id.l3);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();


        setOnClick();

        return view;
    }

    private void setOnClick() {

        YourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoOrderActivity();
            }
        });
    }
}