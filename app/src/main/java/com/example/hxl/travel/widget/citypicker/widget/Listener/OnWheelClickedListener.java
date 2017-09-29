package com.example.hxl.travel.widget.citypicker.widget.Listener;

import com.example.hxl.travel.widget.citypicker.widget.wheel.WheelView;

/**
 * Created by hxl on 2017/1/13 at haiChou.
 * Wheel clicked listener interface.
 * The onItemClicked() method is called whenever a wheel item is clicked
 * new Wheel position is set
 * Wheel view is scrolled
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
