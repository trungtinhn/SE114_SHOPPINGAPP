package com.example.shoppingapp.StaffView.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shoppingapp.StaffView.fragment.chat_fragment_customer;
import com.example.shoppingapp.StaffView.fragment.status_fragment_customer;

public class adapter_chat_board_customer extends FragmentPagerAdapter {

    int tabcount;

    Activity activity;


    public adapter_chat_board_customer(@NonNull FragmentManager fm, int behavior, Activity activity) {
        super(fm, behavior);
        tabcount=behavior;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new chat_fragment_customer();

            case 1:
                return new status_fragment_customer();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

