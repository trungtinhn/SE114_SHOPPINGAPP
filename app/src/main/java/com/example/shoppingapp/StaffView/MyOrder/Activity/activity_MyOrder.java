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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_MyOrder extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ImageButton back_to_Home;
    private Button detail;
    private int [] tabLayoutSize = new int[5];
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
        tabLayoutSize[0] = 0;
        back_to_Home = findViewById(R.id.imgbtn_back);

        adapter.setupBadges(tabLayout);
        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_MyOrder.this, home_page.class);
                startActivity(intent);
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trangthaiRef = db.collection("DONHANG");
        trangthaiRef.whereEqualTo("TrangThai", "Confirm")
                .addSnapshotListener((queryDocumentSnapshots, e) ->{
                    if (e != null) {
                        // Handle the error here if necessary.
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        int confirmCount = queryDocumentSnapshots.size();
                        tabLayoutSize[0] = confirmCount;
                        adapter.updateBadgeValue(0,confirmCount);
                    }

                });
        FirebaseFirestore db_onwait = FirebaseFirestore.getInstance();
        CollectionReference onwaitRef = db_onwait.collection("DONHANG");
        onwaitRef.whereEqualTo("TrangThai", "onwait")
                .addSnapshotListener((queryDocumentSnapshots, e) ->{
                    if (e != null) {
                        // Handle the error here if necessary.
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        int confirmCount = queryDocumentSnapshots.size();
                        tabLayoutSize[1] = confirmCount;
                        adapter.updateBadgeValue(1,confirmCount);
                    }

                });
        FirebaseFirestore db_delivering = FirebaseFirestore.getInstance();
        CollectionReference deliveringRef = db_delivering.collection("DONHANG");
        deliveringRef.whereEqualTo("TrangThai", "delivering")
                .addSnapshotListener((queryDocumentSnapshots, e) ->{
                    if (e != null) {
                        // Handle the error here if necessary.
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        int confirmCount = queryDocumentSnapshots.size();
                        tabLayoutSize[2] = confirmCount;
                        adapter.updateBadgeValue(2,confirmCount);
                    }
                });
        FirebaseFirestore db_delivered = FirebaseFirestore.getInstance();
        CollectionReference deliveredRef = db_delivered.collection("DONHANG");
        deliveredRef.whereEqualTo("TrangThai", "delivered")
                .addSnapshotListener((queryDocumentSnapshots, e) ->{
                    if (e != null) {
                        // Handle the error here if necessary.
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        int confirmCount = queryDocumentSnapshots.size();
                        tabLayoutSize[3] = confirmCount;
                        adapter.updateBadgeValue(3,confirmCount);
                    }

                });
        FirebaseFirestore db_cancel = FirebaseFirestore.getInstance();
        CollectionReference cancelRef = db_cancel.collection("DONHANG");
        cancelRef.whereEqualTo("TrangThai", "cancel")
                .addSnapshotListener((queryDocumentSnapshots, e) ->{
                    if (e != null) {
                        // Handle the error here if necessary.
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        int confirmCount = queryDocumentSnapshots.size();
                        tabLayoutSize[4] = confirmCount;
                        adapter.updateBadgeValue(4,confirmCount);
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
        private BadgeDrawable getBadge(int position) {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            if (tab != null) {
                BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                badgeDrawable.setBadgeGravity(BadgeDrawable.TOP_END);
                badgeDrawable.setMaxCharacterCount(3);
                return badgeDrawable;
            }
            return null;
        }
        private void setupBadges(TabLayout tabLayout) {
            for (int i = 0; i < NUM_PAGES; i++) {
                badgeDrawables[i] = getBadge(i);
                updateBadgeValue(i, tabLayoutSize[i]);
            }
        }
        public void updateBadgeValue(int position, int value) {
            BadgeDrawable badgeDrawable = badgeDrawables[position];
            if (badgeDrawable != null) {
                badgeDrawable.setNumber(value);
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