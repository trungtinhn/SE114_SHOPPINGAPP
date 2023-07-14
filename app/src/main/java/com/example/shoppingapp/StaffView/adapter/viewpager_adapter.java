package com.example.shoppingapp.StaffView.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoppingapp.StaffView.fragment.My_Inventory;
import com.example.shoppingapp.StaffView.fragment.onwait;
import com.example.shoppingapp.StaffView.fragment.out_of_stock_fragment;

public class viewpager_adapter extends FragmentStateAdapter {

    public viewpager_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new My_Inventory();
            case 1:
                return new out_of_stock_fragment();
            case 2:
                return new onwait();
            default:
                return new My_Inventory();
        }
    }

    @Override
    public int getItemCount() {

        return 3;
    }


}
