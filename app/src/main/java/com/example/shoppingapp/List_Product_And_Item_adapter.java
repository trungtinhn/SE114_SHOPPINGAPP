package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class List_Product_And_Item_adapter extends FragmentStateAdapter {


    public List_Product_And_Item_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new My_list_product_fragment();
            case 1:
                return new My_list_item_fragment();
            default:
                return new My_list_product_fragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
