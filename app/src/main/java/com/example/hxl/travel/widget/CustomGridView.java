package com.example.hxl.travel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class CustomGridView extends GridView{

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
        int height = MeasureSpec.makeMeasureSpec(1000000, MeasureSpec.AT_MOST);
        //int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
