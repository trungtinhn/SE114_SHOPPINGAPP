package com.example.shoppingapp.StaffView.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shoppingapp.StaffView.fragment.call_fragment;
import com.example.shoppingapp.StaffView.fragment.chat_fragment;
import com.example.shoppingapp.StaffView.fragment.status_fragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;




    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
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

            case 2:

                return new call_fragment();


            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

