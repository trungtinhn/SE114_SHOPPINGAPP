package com.example.shoppingapp.StaffView.MyOrder.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.cancel_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.confirm_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.delivered_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.delivering_fragment;
import com.example.shoppingapp.StaffView.MyOrder.Fragment.wait_fragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

public class activity_MyOrder extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ImageButton back_to_Home;
    private Button detail;

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

        adapter.setupBadges(tabLayout);
        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_MyOrder.this, home_page.class);
                startActivity(intent);
            }
        });

    }
    private class AdminOrderPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 5;
        private BadgeDrawable[] badgeDrawables = new BadgeDrawable[NUM_PAGES];
        private final String[] TAB_TITLES = {"Confirm", "Wait", "Delivering", "Delivered", "Cancel"};

        private int confirmProductCount = 0;
        public AdminOrderPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
        private void setupBadges(TabLayout tabLayout) {
            for (int i = 0; i < NUM_PAGES; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) {
                    badgeDrawables[i] = tab.getOrCreateBadge();
                    badgeDrawables[i].setBadgeGravity(BadgeDrawable.TOP_END);
                    badgeDrawables[i].setMaxCharacterCount(3); // Điều chỉnh số ký tự tối đa cho giá trị badge
                    updateBadgeValue(i, 0); // Khởi tạo giá trị ban đầu của badge là 0
                }
            }
        }

        // Phương thức để cập nhật giá trị trong badge tương ứng với từng trạng thái
        public void updateBadgeValue(int position, int value) {
            if (position >= 0 && position < NUM_PAGES) {
                badgeDrawables[position].setNumber(value);
            }
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


        // Phương thức để cập nhật số lượng sản phẩm trong trạng thái "Confirm"
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }


    }

}