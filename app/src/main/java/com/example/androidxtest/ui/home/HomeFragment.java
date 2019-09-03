package com.example.androidxtest.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.androidxtest.R;
import com.example.androidxtest.adapter.TabPagerAdapter;
import com.example.androidxtest.ui.send.SendFragment;
import com.example.androidxtest.ui.share.ShareFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
//        viewPager.setOffscreenPageLimit(4);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getChildFragmentManager(), getActivity());
        pagerAdapter.addFragment(new SendFragment(), "Send");
        pagerAdapter.addFragment(new ShareFragment(), "Share");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}