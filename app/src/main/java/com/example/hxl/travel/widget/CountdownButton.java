package com.example.hxl.travel.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by hxl on 2017/1/4 at haiChou.
 * 验证码
 */
public class CountdownButton extends Button implements View.OnClickListener {

    /**
     * 倒计时长，默认60s
     */
    private long length = 60*1000;

    /**
     * 开始执行计时的类 Timer
     */
    private Timer timer;
    /**
     * 执行计时任务的类
     * TimerTask抽象类--抽象方法run()中执行
     */
    private TimerTask timerTask;
    /**
     * 点击按钮执行执行的文字
     */
    private String beforeText = "获取验证码";
    /**
     * 开始计时之后秒数之后显示的文字
     */
    private String afterTest = "秒";

    /**
     * 按钮点击事件
     */
    private OnClickListener onClickListener ;

    public CountdownButton(Context context) {
        super(context);
        initView();
    }

    public CountdownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(getText())){
            beforeText = getText().toString().trim();
        }else {
            this.setText(beforeText);
        }
        //设置点击监听事件
        setOnClickListener(this);
    }
    /**
     * 设置时长
     */
    public void setLength(long length){
        this.length = length;
    }
    /**
     * 点击按钮之前的文字显示
     */
    public void setBeforeText(String beforeText){
        this.beforeText = beforeText;
    }
    /**
     * 设置点击按钮之后秒数之后显示的文字内容
     */
    public void setAfterText(String afterTest){
        this.afterTest = afterTest;
    }
    /**
     * 设置监听按钮点击事件监听
     */
    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener instanceof CountdownButton){
            super.setOnClickListener(onClickListener);
        }else {
            this.onClickListener = onClickListener;
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        //开始计时
        start();
        if (onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    private void start() {
        initTimer();
        this.setText(length/1000 + afterTest);
        this.setEnabled(false);
        timer.schedule(timerTask,0,1000);
    }

    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //发送一个消息
                handler.sendEmptyMessage(1);
            }
        };
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    CountdownButton.this.setText(length/1000+afterTest);
                    length -= 1000;
                    if (length<0){
                        CountdownButton.this.setText(beforeText);
                        CountdownButton.this.setEnabled(true);
                        clearTimer();
                        length = 60*1000;
                    }
                    break;
            }
        }
    };

    private void clearTimer() {
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }
    @Override
    public void onDetachedFromWindow() {
        clearTimer();
        super.onDetachedFromWindow();
    }
}
