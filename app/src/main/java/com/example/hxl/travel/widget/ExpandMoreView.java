package com.example.hxl.travel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.utils.ScreenUtil;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 * 足迹展开更多
 */

public class ExpandMoreView extends LinearLayout {

    /*默认的颜色*/
    private int defaulTextColor = Color.BLACK;
    /*默认的字体大小*/
    private int defaultTextSize = 15;
    /*默认的箭头*/
    private int defaultUpIcon = R.mipmap.ic_map_dialog_down_gray;
    /*下划线的高度*/
    private int defaltLineWidth;
    /*下划线的颜色*/
    private int defaltLineColor = Color.parseColor("#f2f2f2");
    /*景区名称*/
    private TextView titleText;
    private float titleSize;
    private int titleColor;
    private String title;
    /*箭头*/
    private ImageView imageView;
    private int arrowsIcon;
    /*逗留时间*/
    private TextView stayText;
    private float staySize;
    private int stayColor;
    private String stayTitle;
    /*下划线*/
    private View lineOne;
    private float lineWidth;
    private int lineColor;
    private View lineTwo;
    /*游玩时间标题*/
    private TextView platTimeTitleText;
    private float playSize;
    private int playColor;
    private String playTitle = "游玩时间";
    /*游玩时间*/
    private TextView platTimeText;
    private float playTimeSize;
    private int playTimeColor;
    private String playTimeTitle;
    /*消费标题*/
    private TextView consumeTitleText;
    private String consumeTitle = "消费";
    private float consumeTitleSize;
    private int consumeTitleColor;
    /*消费金额*/
    private TextView consumeMoneyText;
    private float consumeMoneyTitleSize;
    private int consumeMoneyTitleColor;
    private String consumeMoneyTitle;
    /*总高度*/
    private int totalHeight;
    /*第一行的高度*/
    private int height;
    private int size;
    private RelativeLayout relativeLayoutTitle;
    private RelativeLayout relativeLayoutPlay;
    private RelativeLayout relativeLayoutConsume;
    /*游玩日期*/
    private TextView playDataText;
    private String playDataTitle;
    private float playDataSize;
    private int playDataColor;
    /*景点图标*/
    private ImageView ivScenic;
    private int scenicIcon;

    /*背景颜色*/
    private int bgColor = Color.parseColor("#88f2f2f2");

    public ExpandMoreView(Context context) {
        this(context, null);
    }

    public ExpandMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*初始化组件*/
        init(context);
        /*属性设置*/
        setAttrs(context, attrs);
        /*监听绑定*/
        bindListener();
        initData();
    }

    private void initData() {
        relativeLayoutPlay.setVisibility(GONE);
        relativeLayoutConsume.setVisibility(GONE);
        lineOne.setVisibility(GONE);
        lineTwo.setVisibility(GONE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        height = view.getHeight();
        size = getChildCount();
        for (int i = 0; i < size; i++) {
            totalHeight += getChildAt(i).getHeight();
        }
        //setMeasuredDimension(widthMeasureSpec,height);
    }


    private void bindListener() {

        relativeLayoutTitle.setOnClickListener(new OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                /*动画时间*/
                int duration = 200;
                isExpand = !isExpand;
                clearAnimation();
                if (isExpand) {
                    RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(duration);
                    rotateAnimation.setFillAfter(true);
                    imageView.startAnimation(rotateAnimation);

                    relativeLayoutPlay.setVisibility(VISIBLE);
                    relativeLayoutConsume.setVisibility(VISIBLE);
                    lineOne.setVisibility(VISIBLE);
                    lineTwo.setVisibility(VISIBLE);
                } else {
                    RotateAnimation rotateAnimation = new RotateAnimation(180f, 0f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(duration);
                    rotateAnimation.setFillAfter(true);
                    imageView.startAnimation(rotateAnimation);

                    relativeLayoutPlay.setVisibility(GONE);
                    relativeLayoutConsume.setVisibility(GONE);
                    lineOne.setVisibility(GONE);
                    lineTwo.setVisibility(GONE);
                }

                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        super.applyTransformation(interpolatedTime, t);
//                        layoutParams.height = (int) (start + delta*interpolatedTime);
//                        setLayoutParams(layoutParams);
                    }

                };

//                animation.setDuration(duration);
//                startAnimation(animation);
                onItemClickListener.itemClick();
            }
        });

    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandMoreView);
        /*标题*/
        title = typedArray.getString(R.styleable.ExpandMoreView_titleText);
        titleSize = typedArray.getDimension(R.styleable.ExpandMoreView_titleSize,
                defaultTextSize);
        titleColor = typedArray.getColor(R.styleable.ExpandMoreView_titleColor,
                defaulTextColor);
        /*游玩日期*/
        playDataTitle = typedArray.getString(R.styleable.ExpandMoreView_playDataText);
        playDataSize = typedArray.getDimension(R.styleable.ExpandMoreView_playDataSize,
                defaultTextSize);
        playDataColor = typedArray.getColor(R.styleable.ExpandMoreView_playDataColor,
                defaulTextColor);

        /*逗留时间*/
        stayTitle = typedArray.getString(R.styleable.ExpandMoreView_stayText);
        staySize = typedArray.getDimension(R.styleable.ExpandMoreView_staySize,
                defaultTextSize);
        stayColor = typedArray.getColor(R.styleable.ExpandMoreView_stayColor,
                defaulTextColor);
        /*箭头*/
        arrowsIcon = typedArray.getResourceId(R.styleable.ExpandMoreView_arrowsIcon, defaultUpIcon);
        /*游玩标题*/
        playSize = typedArray.getDimension(R.styleable.ExpandMoreView_playSize,
                defaultTextSize);
        playColor = typedArray.getColor(R.styleable.ExpandMoreView_playColor,
                defaulTextColor);
        /*游玩时间*/
        playTimeTitle = typedArray.getString(R.styleable.ExpandMoreView_playTimeText);
        playTimeSize = typedArray.getDimension(R.styleable.ExpandMoreView_playTimeSize,
                defaultTextSize);
        playTimeColor = typedArray.getColor(R.styleable.ExpandMoreView_playTimeColor,
                defaulTextColor);
        /*消费标题*/
        consumeTitleSize = typedArray.getDimension(R.styleable.ExpandMoreView_consumeTitleSize,
                defaultTextSize);
        consumeTitleColor = typedArray.getColor(R.styleable.ExpandMoreView_consumeTitleColor,
                defaulTextColor);
        /*消费金额*/
        consumeMoneyTitle = typedArray.getString(R.styleable.ExpandMoreView_consumeMoneyText);
        consumeMoneyTitleSize = typedArray.getDimension(R.styleable.
                ExpandMoreView_consumeMoneyTitleSize, defaultTextSize);
        consumeMoneyTitleColor = typedArray.getColor(R.styleable.
                ExpandMoreView_consumeMoneyTitleColor, defaulTextColor);
        /*下划线*/
        lineWidth = typedArray.getDimension(R.styleable.ExpandMoreView_lineWidth,
                defaltLineWidth);
        lineColor = typedArray.getColor(R.styleable.ExpandMoreView_lineFootprintColor,
                defaltLineColor);

        /*景点图标*/
        scenicIcon = typedArray.getResourceId(R.styleable.ExpandMoreView_scenicIcon,
                R.mipmap.ic_map_tab_scenic_checked);

        setViewAttrs();
        /*回收*/
        typedArray.recycle();
    }

    public void setTitle(String title) {
        this.title = title;
        titleText.setText(title);
    }

    public void setStayTitle(String stayTitle) {
        this.stayTitle = stayTitle;
        stayText.setText(stayTitle);
    }

    public void setPlayDataTitle(String playDataTitle) {
        this.playDataTitle = playDataTitle;
        playDataText.setText(playDataTitle);
    }

    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
        platTimeText.setText(playTitle);
    }

    public void setConsumeMoneyTitle(String consumeMoneyTitle) {
        this.consumeMoneyTitle = consumeMoneyTitle;
        consumeMoneyText.setText(consumeMoneyTitle);
    }

    public void setScenicIcon(int scenicIcon) {
        Log.e("icon", "setScenicIcon: "+scenicIcon);
        this.scenicIcon = scenicIcon;
        ivScenic.setImageResource(scenicIcon);
    }

    private void setViewAttrs() {
        titleText.setText(title);
        titleText.setTextSize(titleSize);
        titleText.setTextColor(titleColor);
        imageView.setImageResource(arrowsIcon);
        playDataText.setText(playDataTitle);
        playDataText.setTextColor(playDataColor);
        playDataText.setTextSize(playDataSize);
        stayText.setText(stayTitle);
        stayText.setTextSize(staySize);
        stayText.setTextColor(stayColor);
        lineOne.setBackgroundColor(lineColor);
        lineTwo.setBackgroundColor(lineColor);
        platTimeTitleText.setText(playTitle);
        platTimeTitleText.setTextColor(playColor);
        platTimeTitleText.setTextSize(playSize);
        platTimeText.setText(playTimeTitle);
        platTimeText.setTextColor(playTimeColor);
        platTimeText.setTextSize(playTimeSize);
        consumeTitleText.setText(consumeTitle);
        consumeTitleText.setTextColor(consumeTitleColor);
        consumeTitleText.setTextSize(consumeTitleSize);
        consumeMoneyText.setText(consumeMoneyTitle);
        consumeMoneyText.setTextColor(consumeMoneyTitleColor);
        consumeMoneyText.setTextSize(consumeMoneyTitleSize);

        ivScenic.setImageResource(scenicIcon);
    }


    public void setArrowsIcon(int arrowsIcon) {
        this.arrowsIcon = arrowsIcon;
    }


    private void init(Context context) {
        defaltLineWidth = ScreenUtil.dip2px(context,1);
        /*竖直方向*/
        setOrientation(VERTICAL);
        RelativeLayout.LayoutParams rlLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams lineLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(1));
        int margin = dp2px(10);
        int paddingTwenty = dp2px(20);
        int paddingTen = dp2px(10);
        lineLayoutParams.setMargins(margin, 0, margin, 0);
        //rlLayoutParams.setMargins(margin, 0, margin, 0);

        /*标题*/
        relativeLayoutTitle = new RelativeLayout(context);
        relativeLayoutTitle.setGravity(Gravity.CENTER_VERTICAL);
        relativeLayoutTitle.setPadding(paddingTwenty, paddingTen, paddingTwenty, paddingTen);
        relativeLayoutTitle.setLayoutParams(rlLayoutParams);

        /*景点图标*/
        ivScenic = new ImageView(context);
        RelativeLayout.LayoutParams iconScenicLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        iconScenicLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        iconScenicLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        ivScenic.setId(Integer.valueOf(1));
        ivScenic.setLayoutParams(iconScenicLayoutParams);
        /*景区名称*/
        titleText = new TextView(context);
        RelativeLayout.LayoutParams titleLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, ivScenic.getId());
        titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        titleLayoutParams.setMargins(dp2px(5),0,0,0);
        titleText.setLayoutParams(titleLayoutParams);
        /*箭头图标*/
        imageView = new ImageView(context);
        RelativeLayout.LayoutParams iconLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        iconLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        iconLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(iconLayoutParams);
        imageView.setImageResource(R.mipmap.ic_map_dialog_up);
        imageView.setId(Integer.valueOf(2));
        iconLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

         /*逗留时间*/
        stayText = new TextView(context);
        stayText.setId(Integer.valueOf(3));
        RelativeLayout.LayoutParams stayLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        stayText.setLayoutParams(stayLayoutParams);
        stayLayoutParams.setMargins(0, 0, margin, 0);
        stayLayoutParams.addRule(RelativeLayout.LEFT_OF, imageView.getId());
        stayText.setGravity(Gravity.CENTER_VERTICAL);
        /*游玩日期*/
        playDataText = new TextView(context);
        RelativeLayout.LayoutParams playDataLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        playDataText.setLayoutParams(playDataLayoutParams);
        playDataLayoutParams.setMargins(0, 0, margin, 0);
        playDataLayoutParams.addRule(RelativeLayout.LEFT_OF, stayText.getId());
        playDataText.setGravity(Gravity.CENTER_VERTICAL);

        relativeLayoutTitle.addView(titleText);
        relativeLayoutTitle.addView(ivScenic);
        relativeLayoutTitle.addView(imageView);
        relativeLayoutTitle.addView(playDataText);
        relativeLayoutTitle.addView(stayText);

        lineOne = new View(context);
        lineOne.setLayoutParams(lineLayoutParams);


        /*游玩时间*/
        relativeLayoutPlay = new RelativeLayout(context);
        relativeLayoutPlay.setGravity(Gravity.CENTER_VERTICAL);
        relativeLayoutPlay.setPadding(paddingTwenty, paddingTen, paddingTwenty, paddingTen);
        relativeLayoutPlay.setLayoutParams(rlLayoutParams);
        relativeLayoutPlay.setBackgroundColor(bgColor);
        /*游玩时间title*/
        platTimeTitleText = new TextView(context);
        RelativeLayout.LayoutParams playTitleLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        playTitleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        playTitleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        platTimeTitleText.setLayoutParams(playTitleLayoutParams);
        /*游玩时间time*/
        platTimeText = new TextView(context);
        RelativeLayout.LayoutParams playTimeLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        playTimeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        playTimeLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        platTimeText.setLayoutParams(playTimeLayoutParams);

        relativeLayoutPlay.addView(platTimeTitleText);
        relativeLayoutPlay.addView(platTimeText);

        lineTwo = new View(context);
        lineTwo.setLayoutParams(lineLayoutParams);

        /*消费*/
        relativeLayoutConsume = new RelativeLayout(context);
        relativeLayoutConsume.setGravity(Gravity.CENTER_VERTICAL);
        relativeLayoutConsume.setPadding(paddingTwenty, paddingTen, paddingTwenty, paddingTen);
        relativeLayoutConsume.setBackgroundColor(bgColor);
        relativeLayoutConsume.setLayoutParams(rlLayoutParams);
        /*消费title*/
        consumeTitleText = new TextView(context);
        RelativeLayout.LayoutParams consumeTitleLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        consumeTitleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        consumeTitleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        consumeTitleText.setLayoutParams(consumeTitleLayoutParams);
        /*消费money*/
        consumeMoneyText = new TextView(context);
        RelativeLayout.LayoutParams consumeMoneyLayoutParams = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        consumeMoneyLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        consumeMoneyLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        consumeMoneyText.setLayoutParams(consumeMoneyLayoutParams);

        relativeLayoutConsume.addView(consumeTitleText);
        relativeLayoutConsume.addView(consumeMoneyText);

        /**/
        addView(relativeLayoutTitle);
        addView(lineOne);
        addView(relativeLayoutPlay);
        addView(lineTwo);
        addView(relativeLayoutConsume);
    }

    private int dp2px(int value) {
        /*获得比例*/
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }


    public interface OnItemClickListener{
        void itemClick();
    }
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}