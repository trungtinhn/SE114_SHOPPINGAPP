package com.example.shoppingapp.StaffView.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoppingapp.StaffView.fragment.confirm_fragment;
import com.example.shoppingapp.StaffView.fragment.delivered_fragment;
import com.example.shoppingapp.StaffView.fragment.delivering_fragment;
import com.example.shoppingapp.StaffView.fragment.wait_fragment;

public class viewpager_adapter_for_order extends FragmentStateAdapter {

    public viewpager_adapter_for_order(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new confirm_fragment();
            case 1:
                return new wait_fragment();
            case 2:
                return new delivered_fragment();
            case 3:
                return new delivering_fragment();
            default:
                return new confirm_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
