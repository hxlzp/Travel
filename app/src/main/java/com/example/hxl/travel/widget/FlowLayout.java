package com.example.hxl.travel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/8/11 0011 at haichou.
 * 流式布局
 */

public class  FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量子view的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content width和height就需要我们自己计算了
        //存储wrap_content时的宽度和高度
        //如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //获得内部子元素的个数
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++){
            //得到子View
            View child = getChildAt(i);

            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //测量完成后，我们就可以获得子view的宽和高
            //子view得到的getLayoutParams是根据它所在的父view决定的
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            //子view占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;

            //判断，当前的行宽+子view的宽度大于容器的宽度，需要换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){
                //第一行的宽度(当前的宽度和我们已经记录的做个对比)
                width = Math.max(width, lineWidth);
                //重置行宽lineWidth
                lineWidth = childWidth;
                //换行高度叠加
                height += lineHeight;
                //记录行高,重置行高
                lineHeight = childHeight;
            }else { //不换行时
                //宽度叠加
                lineWidth += childWidth;
                //高度和子view相比最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //特殊情况最后一个控件
            if (i == cCount - 1){
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }

    /**
     * 存储所有的view(一行一行的存储)
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    /**
     * 设置子view的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        //当前ViewGroup的宽度
        int width = getWidth();

        //每一行的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<View>();

        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            //获得子view
            View childView = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            //获得子view的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //如果需要换行
            if(childWidth + lineWidth + params.rightMargin + params.leftMargin>width-getPaddingLeft()-getPaddingRight()){
                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的View
                mAllViews.add(lineViews);

                //重置行宽和行高
                lineWidth = 0;
                lineHeight = childHeight+params.topMargin+params.bottomMargin;
                //重置view集合
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + params.rightMargin + params.leftMargin ;
            lineHeight = Math.max(lineHeight,childHeight+params.topMargin+params.bottomMargin);
            lineViews.add(childView);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        int lineNum = mAllViews.size();
        for(int i = 0;i<lineNum;i++){
            //当前行所有的view
            lineViews = mAllViews.get(i);
            //当前行的高度
            lineHeight = mLineHeight.get(i);
            for(int j = 0;j<lineViews.size();j++){
                View childView = lineViews.get(j);
                //判断child的状态
                if (childView.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                int lc = left + params.leftMargin;
                int tc = top + params.topMargin;
                int rc = lc + childView.getMeasuredWidth();
                int bc = tc + childView.getMeasuredHeight();

                //为子view布局
                childView.layout(lc,tc,rc,bc);

                //第一个布局完成后,left改变，top不变
                left += childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            }
            //下一行
            left = getPaddingLeft();
            top += lineHeight + getPaddingTop();

        }
    }

    /**
     * 与当前对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
