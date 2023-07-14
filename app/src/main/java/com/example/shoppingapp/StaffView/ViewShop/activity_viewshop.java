

package com.example.shoppingapp.StaffView.ViewShop;


import android.os.Bundle;
import android.support.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.ViewShop.Fragment.fragment_listItems;
import com.example.shoppingapp.StaffView.ViewShop.Fragment.fragment_products;
import com.google.android.material.tabs.TabLayout;

public class activity_viewshop extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_view_shop);
        tabLayout = findViewById(R.id.tab_layout_listItem_Products);
        viewPager = findViewById(R.id.viewPager_listItem_Product);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private static class PagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 2;
        private static final String[] TAB_TITLES = {"Products", "List Items"};

        public PagerAdapter(FragmentManager fm) {
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
                    return new fragment_products();
                case 1:
                    return new fragment_listItems();

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
