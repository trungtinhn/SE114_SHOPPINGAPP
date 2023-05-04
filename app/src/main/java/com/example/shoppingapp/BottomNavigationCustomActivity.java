package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoppingapp.categories.Categories;
import com.example.shoppingapp.categories.CategoriesAdapter;
import com.example.shoppingapp.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.fragment.DetailProductFragment;
import com.example.shoppingapp.fragment.FollowFragment;
import com.example.shoppingapp.fragment.NotificationFragment;
import com.example.shoppingapp.fragment.ViewPagerAdapter;
import com.example.shoppingapp.product.Product;
import com.example.shoppingapp.product.ProductAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationCustomActivity extends AppCompatActivity{


    private ViewPager view_pager;
    private BottomNavigationView BottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_custom);

        view_pager = findViewById(R.id.view_pager);
        BottomNavigationView = findViewById(R.id.bottomNavigationView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        BottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;

                    case 1:
                        BottomNavigationView.getMenu().findItem(R.id.menu_notification).setChecked(true);
                        break;

                    case 2:
                        BottomNavigationView.getMenu().findItem(R.id.menu_follow).setChecked(true);
                        break;

                    case 3:
                        BottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        view_pager.setCurrentItem(0);
                        break;

                    case R.id.menu_notification:
                        view_pager.setCurrentItem(1);
                        break;

                    case R.id.menu_follow:
                        view_pager.setCurrentItem(2);
                        break;

                    case R.id.menu_account:
                        view_pager.setCurrentItem(3);
                        break;
                }

                return true;
            }
        });

    }

    public void gotoDetailProduct(Product product)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framehomepragment, new DetailProductFragment());
        fragmentTransaction.commit();


    }


}