package com.example.shoppingapp.StaffView.MyProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.example.shoppingapp.StaffView.MyProduct.Fragment.my_inventory_fragment;
import com.example.shoppingapp.StaffView.MyProduct.Fragment.onwait_fragment;
import com.example.shoppingapp.StaffView.MyProduct.Fragment.out_of_stock_fragment;
import com.google.android.material.tabs.TabLayout;

public class activity_MyProduct extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Button back_to_Home;


    private Button addnew, hide, edit, search;
    private TextView tv_MyProduct;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_myproduct);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        AdminOrderPagerAdapter adapter = new AdminOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        search = findViewById(R.id.btn_search);
        back_to_Home = findViewById(R.id.imgbtn_back);
        addnew = findViewById(R.id.add_product);
        searchView = findViewById(R.id.searchView);
        tv_MyProduct = findViewById(R.id.My_product);

        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_MyProduct.this, home_page.class);
                startActivity(intent);
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_MyProduct.this, activity_add_new_product.class);
                startActivity(intent);
            }
        });
    }
    private static class AdminOrderPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 3;
        private static final String[] TAB_TITLES = {"My Inventory", "Out Of Stocks", "On Wait"};

        public AdminOrderPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new my_inventory_fragment();
                case 1:
                    return new out_of_stock_fragment();
                case 2:
                    return new onwait_fragment();
                default:
                    throw new IllegalStateException("Invalid position: " + position);
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }

}