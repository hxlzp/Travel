package com.example.hxl.travel.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * Created by hxl on 2016/12/23 at haiChou.
 */
public class UnScrollViewPager extends ViewPager{
    private boolean isScrollable = false;
    private Context mContext;

    public UnScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollViewPager(Context context) {
        super(context);
        this.mContext = context;
        Scroller scroller = new Scroller(mContext);

    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrollable)
            return super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isScrollable)
            return super.onInterceptTouchEvent(event);
        return false;
    }
}
