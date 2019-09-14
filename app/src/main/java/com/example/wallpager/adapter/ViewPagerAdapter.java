package com.example.wallpager.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wallpager.base.BaseFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter {
    private BaseFragment[] fms;
    public ViewPagerAdapter(@NonNull FragmentManager fm, BaseFragment...fms) {
        super(fm);
        this.fms = fms;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fms[position];
    }

    @Override
    public int getCount() {
        return fms.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fms[position].getTitle();
    }
}
