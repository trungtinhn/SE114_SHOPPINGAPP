package com.example.shoppingapp.customerview.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.cancel_fragment_Customer;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.confirm_fragment_Customer;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.delivered_fragment_Customer;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.delivering_fragment_Customer;
import com.example.shoppingapp.customerview.fragment.My_Order_fragment.wait_fragment_Customer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ImageView back_to_Home;
    private Button detail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        tabLayout = findViewById(R.id.tab_layout_order);
        viewPager = findViewById(R.id.view_pager_order);
        OrderActivity.UserOrderPagerAdapter adapter = new OrderActivity.UserOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        back_to_Home = findViewById(R.id.imgbtn_back_user);

        adapter.setupBadges(tabLayout);
        back_to_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });

    }

    private class UserOrderPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 5;
        private BadgeDrawable[] badgeDrawables = new BadgeDrawable[NUM_PAGES];
        private final String[] TAB_TITLES = {"Confirm", "Wait", "Delivering", "Delivered", "Cancel"};

        private int confirmProductCount = 0;

        public UserOrderPagerAdapter(FragmentManager fm) {
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
                    setBadges();
                }
            }
        }

        private void setBadges() {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            // Badge Confirm
            firebaseFirestore.collection("DONHANG")
                    .whereEqualTo("MaND", firebaseUser.getUid())
                    .whereEqualTo("TrangThai", "Confirm")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            updateBadgeValue(0, value.size());
                        }
                    });

            // Badge Wait
            firebaseFirestore.collection("DONHANG")
                    .whereEqualTo("MaND", firebaseUser.getUid())
                    .whereEqualTo("TrangThai", "Wait")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            updateBadgeValue(1, value.size());
                        }
                    });

            // Badge Delivering
            firebaseFirestore.collection("DONHANG")
                    .whereEqualTo("MaND", firebaseUser.getUid())
                    .whereEqualTo("TrangThai", "Delivering")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            updateBadgeValue(2, value.size());
                        }
                    });

            //Badge Delivered
            firebaseFirestore.collection("DONHANG")
                    .whereEqualTo("MaND", firebaseUser.getUid())
                    .whereEqualTo("TrangThai", "Delivered")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            updateBadgeValue(3, value.size());
                        }
                    });

            // Badge Cancel
            firebaseFirestore.collection("DONHANG")
                    .whereEqualTo("MaND", firebaseUser.getUid())
                    .whereEqualTo("TrangThai", "Cancel")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            updateBadgeValue(4, value.size());
                        }
                    });


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
                    return new confirm_fragment_Customer();
                case 1:
                    return new wait_fragment_Customer();
                case 2:
                    return new delivering_fragment_Customer();
                case 3:
                    return new delivered_fragment_Customer();
                case 4:
                    return new cancel_fragment_Customer();
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