package com.example.hxl.travel.widget.bezierViewPager;

import android.support.v7.widget.CardView;

/**
 * Created by hxl on 2017/4/17 at haiChou.
 */
interface CardAdapter {
    CardView getCardViewAt(int position);

    int getCount();

    int getMaxElevationFactor();

    void setMaxElevationFactor(int MaxElevationFactor);
}

