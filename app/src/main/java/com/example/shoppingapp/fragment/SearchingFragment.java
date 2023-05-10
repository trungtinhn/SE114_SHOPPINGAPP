package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shoppingapp.BottomNavigationCustomActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.product.ProductCard;
import com.example.shoppingapp.product.ProductCardAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchingFragment extends Fragment {

    private RecyclerView rcvSearching;
    BottomNavigationCustomActivity bottomNavigationCustomActivity;
    ProductCardAdapter productCardAdapter;
    List<ProductCard> mSearching;
    ImageView backICon;
    EditText editSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_searching, container, false);
        rcvSearching = view.findViewById(R.id.rcvSearching);
        backICon = view.findViewById(R.id.backIcon);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();
        editSearch = view.findViewById(R.id.editSearch);


        setRcvProductSearching();
        setOnClickBackIcon();

        return view;
    }

    private void setOnClickBackIcon() {
        backICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoHomeFragment();
            }
        });
    }

    private void setRcvProductSearching() {
       productCardAdapter = new ProductCardAdapter();
       mSearching = new ArrayList<>();

       mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 1", 299999));
       mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 2", 299999));

       mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));

       mSearching.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));
       mSearching.add(new ProductCard(R.drawable.mauaodep, "Giay loai 3", 299999));


       GridLayoutManager gridLayoutManager = new GridLayoutManager(bottomNavigationCustomActivity, 2);
       rcvSearching.setLayoutManager(gridLayoutManager);
       productCardAdapter.setData(mSearching);
       rcvSearching.setAdapter(productCardAdapter);
    }
}