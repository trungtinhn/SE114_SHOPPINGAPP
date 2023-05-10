package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoppingapp.BottomNavigationCustomActivity;
import com.example.shoppingapp.R;



public class DetailProductFragment extends Fragment {

    private ImageView backIcon;
    private BottomNavigationCustomActivity bottomNavigationCustomActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        backIcon = view.findViewById(R.id.backIcon);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();

        setOnClickBackICon();



        return view;
    }

    private void setOnClickBackICon() {

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoHomeFragment();
            }
        });
    }
}