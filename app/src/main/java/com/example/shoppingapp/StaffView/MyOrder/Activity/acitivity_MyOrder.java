package com.example.shoppingapp.StaffView.MyOrder.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.MyOrder.Home.activity_home;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.cancel_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.confirm_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.delivered_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.delivering_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.wait_fragment;
import com.google.android.material.tabs.TabLayout;

public class acitivity_MyOrder extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ImageButton back_to_Home;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_order);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        AdminOrderPagerAdapter adapter = new AdminOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        back_to_Home = findViewById(R.id.imgbtn_back);
        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(acitivity_MyOrder.this, activity_home.class);
                startActivity(intent);
            }
        });

    }
    private static class AdminOrderPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 5;
        private static final String[] TAB_TITLES = {"Confirm", "Wait", "Delivering", "Delivered", "Cancel"};

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
                    return new confirm_fragment();
                case 1:
                    return new wait_fragment();
                case 2:
                    return new delivering_fragment();
                case 3:
                    return new delivered_fragment();
                case 4:
                    return new cancel_fragment();
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