package com.example.androidxtest.adapter;

import android.content.Context;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFragmentList = new ArrayList();
    private ArrayList<String> mFragmentTitleNames = new ArrayList();
    private Context mContext;

    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleNames.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleNames.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
