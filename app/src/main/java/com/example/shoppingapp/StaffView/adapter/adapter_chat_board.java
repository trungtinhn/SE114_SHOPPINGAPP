package com.example.shoppingapp.StaffView.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shoppingapp.StaffView.fragment.chat_fragment;
import com.example.shoppingapp.StaffView.fragment.status_fragment;

public class adapter_chat_board extends FragmentPagerAdapter {

    int tabcount;




    public adapter_chat_board(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new chat_fragment();

            case 1:
                return new status_fragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

