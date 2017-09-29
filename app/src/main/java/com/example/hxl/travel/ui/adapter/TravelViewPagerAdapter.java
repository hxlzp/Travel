package com.example.hxl.travel.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hxl on 2017/8/9 0009 at haichou.
 */

public class TravelViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments ;
    private List<String> tabs;
    public TravelViewPagerAdapter(FragmentManager fm,
                                  List<Fragment> fragments ,List<String> tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs= tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null?0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabs.get(position);
    }
}
