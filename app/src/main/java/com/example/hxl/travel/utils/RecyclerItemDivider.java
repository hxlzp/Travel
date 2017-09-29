package com.example.hxl.travel.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hxl.travel.R;

/**
 * Created by hxl on 2017/1/18 at haiChou.
 */
public class RecyclerItemDivider extends RecyclerView.ItemDecoration{
    /*上下文*/
    private Context mContext ;
    /*方向*/
    private int mOrientation ;

    private Drawable mDivider;
    /*水平方向*/
    private final static int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    /*竖直方向*/
    private final static int VERTICAL = LinearLayoutManager.VERTICAL;

    public RecyclerItemDivider(Context mContext, int mOrientation) {
        this.mContext = mContext;
        this.mOrientation = mOrientation;
        mDivider = mContext.getDrawable(R.drawable.divider_gray);
        setOrientation(mOrientation);
    }

    private void setOrientation(int mOrientation) {
        if (mOrientation != HORIZONTAL && mOrientation != VERTICAL){
            throw new IllegalStateException("invalid orientation");
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL){//竖直方向画横线
            drawHorizontalLine(c, parent, state);
        } else if (mOrientation == HORIZONTAL){//水平方向画竖线
            drawVerticalLine(c, parent, state);
        }
    }

    /**
     * 画竖线
     */
    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        //获得子view的数量
        int childCount = parent.getChildCount();
        //遍历获得子view
        for (int i = 0; i<childCount;i++){
            View childView = parent.getChildAt(childCount);
            //获得子view的布局参数
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView
                    .getLayoutParams();
            int rightMargin = layoutParams.rightMargin;
            int left = childView.getRight() + rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

        }
    }

    /**
     * 画横线
     */
    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //获得子view的数量
        int childCount = parent.getChildCount();
        //遍历获得子view
        for(int i = 0 ;i< childCount ;i++){
            View childView = parent.getChildAt(i);
            //获得子view的布局参数
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView
                    .getLayoutParams();
            int bottomMargin = layoutParams.bottomMargin;
            int top = childView.getBottom() + bottomMargin;
            int bottom = top + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //画横线
        if (mOrientation == HORIZONTAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else if (mOrientation == VERTICAL){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}
