package com.example.hxl.travel.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by hxl on 2016/12/23 at haiChou.
 */
public class ScreenUtil {

    /**
     * dpתpx
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * pxתdp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * screenWidth
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * screenHeight
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
    public static int heightMeasure(int heightMeasureSpec) {
        int result = 0;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == View.MeasureSpec.EXACTLY){//精确值(固定值或Match_parent)
            result = heightSize;
        }else {
            result = 200;
            if (heightMode == View.MeasureSpec.AT_MOST){//最大值Wrap_content
                result = Math.min(result,heightSize);
            }
        }
        return result;
    }

    public static int widthMeasure(int widthMeasureSpec) {
        int result = 0;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == View.MeasureSpec.EXACTLY){//精确值(固定值或Match_parent)
            result = widthSize;
        }else {
            result = 200;
            if (widthMode == View.MeasureSpec.AT_MOST){//最大值Wrap_content
                result = Math.min(result,widthSize);
            }
        }
        return result;
    }
}
