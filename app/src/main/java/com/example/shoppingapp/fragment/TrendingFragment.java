package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoppingapp.BottomNavigationCustomActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.product.ProductCard;
import com.example.shoppingapp.product.ProductCardAdapter;

import java.util.ArrayList;
import java.util.List;


public class TrendingFragment extends Fragment {

    private RecyclerView rcvProductTrending;
    BottomNavigationCustomActivity bottomNavigationCustomActivity;
    ProductCardAdapter productCardAdapter;
    List<ProductCard> mTrendingCard;

    ImageView backICon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        rcvProductTrending = view.findViewById(R.id.rcvProductTrending);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();
        backICon = view.findViewById(R.id.backIcon);

        setRcvProductTrending();
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

    private void setRcvProductTrending() {
        productCardAdapter = new ProductCardAdapter();
        mTrendingCard = new ArrayList<>();

        mTrendingCard.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 1", 299999));
        mTrendingCard.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 2", 299999));

        mTrendingCard.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));

        mTrendingCard.add(new ProductCard(R.drawable.anhgiay1, "Giay loai 3", 299999));
        mTrendingCard.add(new ProductCard(R.drawable.mauaodep, "Giay loai 3", 299999));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(bottomNavigationCustomActivity, 2);
        rcvProductTrending.setLayoutManager(gridLayoutManager);
        productCardAdapter.setData(mTrendingCard);
        rcvProductTrending.setAdapter(productCardAdapter);

    }
}