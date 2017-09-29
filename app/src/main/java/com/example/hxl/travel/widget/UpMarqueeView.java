package com.example.hxl.travel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.hxl.travel.R;

import java.util.List;

/**
 * Created by hxl on 2017/8/8 0008 at haichou.
 * 垂直跑马灯
 */

public class UpMarqueeView extends ViewFlipper{
    private int interval = 3000;
    public UpMarqueeView(Context context) {
        this(context,null);
    }

    public UpMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*设置间隔*/
        setFlipInterval(interval);
        /*进入时的动画*/
        Animation animationIn = AnimationUtils.loadAnimation(context, R.anim.marquee_in);
        setInAnimation(animationIn);
        /*离开时的动画*/
        Animation animationOut = AnimationUtils.loadAnimation(context,R.anim.marquee_out);
        setOutAnimation(animationOut);
    }
    /*设置滚动的view集合*/
    public void setViews(final List<View> views){
        for(int i = 0;i<views.size();i++){
            if(views.size() == 0 && views ==null){
                return;
            }
            final int position = i;
            /*监听回调*/
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(views.get(position),position);
                }
            });
            ViewGroup viewGroup = (ViewGroup) views.get(i).getParent();
            if(viewGroup != null){
                viewGroup.removeAllViews();
            }
            addView(views.get(i));
        }
    }
    /*开启*/
    public void startFlip(){
        startFlipping();
    }
    /*停止*/
    public void stopFlip(){
        stopFlipping();
    }
    /*设置对外访问的接口*/
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener ;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
