package com.example.hxl.travel.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 */
public abstract class RootView<T extends BasePresenter> extends LinearLayout {
    /**
     * mActive是否属于当前活跃view，它的判定主要放在onAttachedToWindow和onDetachedFromWindow，
     * 在Presenter根据view是否活跃来取决于是否继续做一些耗时操作（继续读取网络数据等）
     */
    protected boolean mActive;//是否被销毁
    protected Context mContext;//上下文
    protected T mPresenter;
    /**
     * ButterKnife使用心得：
     * 1.Activity ButterKnife.bind(this);必须在setContentView();之后，且父类bind绑定后，
     * 子类不需要再bind
     * 2.Fragment ButterKnife.bind(this, mRootView);
     * 3.属性布局不能用private or static 修饰，否则会报错
     * 4.setContentView()不能通过注解实现。（其他的有些注解框架可以）
     */
    protected Unbinder unbinder;
    public RootView(Context context) {
        super(context);
        init();
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mContext = getContext();
        /**
         * 先getLayout()获得视图再bind绑定视图，不然报找不到...错误
         */
        getLayout();
        //绑定视图
        unbinder = ButterKnife.bind(this);
        mActive = true;
        initView();
        initEvent();
    }

    /**
     * 获得布局文件
     */
    protected abstract void getLayout();

    /**
     * 初始化布局文件
     */
    protected abstract void initView();

    /**
     * 事件
     */
    protected abstract void initEvent();

    /**
     * 绑定窗口
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        mActive = true;
    }
    /**
     * 解绑窗口
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        mActive = false;
    }
}
