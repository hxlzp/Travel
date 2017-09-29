package com.example.hxl.travel.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class FriendViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tab;
    public FriendViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tab) {
        super(fm);
        this.fragments = fragments;
        this.tab = tab;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab.get(position);
    }

}
