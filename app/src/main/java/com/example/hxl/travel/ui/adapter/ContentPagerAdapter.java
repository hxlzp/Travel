package com.example.hxl.travel.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hxl on 2016/12/23 at haiChou.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * 获得Fragment的总条目数
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
    /**
     * 获得Fragment的条目
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}
