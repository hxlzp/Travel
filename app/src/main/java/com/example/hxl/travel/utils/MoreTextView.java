package com.example.hxl.travel.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hxl.travel.R;

/**
 * Created by hxl on 2017/8/16 0016 at haichou.
 * 展开与收起
 */

public class MoreTextView extends LinearLayout{
    private TextView contentView; //文本正文
    private ImageView expandView; //展开按钮
    //对应styleable中的属性
    private int textColor;
    private float textSize;
    private int maxLine;
    private String text;

    //默认属性值
    private int defaultTextColor = Color.BLACK;
    private int defaultTextSize = 12;
    private int defaultLine = 3;

    public MoreTextView(Context context) {
        this(context,null);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(context, attrs);
        bindListener();
    }
    /*绑定监听*/
    private void bindListener() {
        setOnClickListener(new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                contentView.clearAnimation();
                final int deltaValue;
                final int startValue = contentView.getHeight();
                int durationMillis = 500;
                if (isExpand) {
                    deltaValue = contentView.getLineHeight() * contentView.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(0, 180,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                } else {
                    deltaValue = contentView.getLineHeight() * maxLine - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        contentView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }

                };
                animation.setDuration(durationMillis);
                contentView.startAnimation(animation);
            }
        });
    }
    /*取值并设置*/
    private void initWithAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MoreTextView);
        int textColor = a.getColor(R.styleable.MoreTextView_textColor,
                defaultTextColor);  //取颜色值，默认defaultTextColor
        //取颜字体大小，默认defaultTextSize
        textSize = a.getDimensionPixelSize(R.styleable.MoreTextView_textSize, defaultTextSize);
        maxLine = a.getInt(R.styleable.MoreTextView_maxLine, defaultLine);//取颜显示行数，默认defaultLine
        text = a.getString(R.styleable.MoreTextView_text);//取文本内容
        bindTextView(textColor,textSize,maxLine,text);
        a.recycle();//回收释放
    }

    private void bindTextView(int textColor, float textSize, int maxLine, String text) {
        contentView.setTextColor(textColor);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        contentView.setText(text);
        contentView.setHeight(contentView.getLineHeight() * maxLine);
    }

    /*初始化并添加View。初始化TextView和ImageView,并添加到MoretextView中去*/
    private void init() {
        setOrientation(VERTICAL); //设置垂直布局
        setGravity(Gravity.RIGHT); //右对齐
        //初始化textView并添加
        contentView = new TextView(getContext());
        addView(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //初始化ImageView并添加
        expandView = new ImageView(getContext());
        int padding = ScreenUtil.dip2px(getContext(), 5);
        expandView.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams layoutParamsImg= new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        expandView.setImageResource(R.drawable.text_expand_pack);
        addView(expandView, layoutParamsImg);
    }
}
