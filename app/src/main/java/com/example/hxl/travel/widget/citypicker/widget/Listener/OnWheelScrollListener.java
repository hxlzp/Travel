package com.example.hxl.travel.widget.citypicker.widget.Listener;

import com.example.hxl.travel.widget.citypicker.widget.wheel.WheelView;

/**
 * Created by hxl on 2017/1/13 at haiChou.
 * Wheel scrolled listener interface.
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}