package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.WelcomeContract;
import com.example.hxl.travel.ui.activitys.WelcomeActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.ImageLoader;
import com.example.hxl.travel.utils.JumpUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */

public class WelcomeView extends RootView<WelcomeContract.Presenter> implements
        WelcomeContract.View{
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    public WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeView(Context context) {
        super(context);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    /**
     * 加载布局文件
     */
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_welcome_view, this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showContent(List<String> list) {
        if (list!=null){
            //int page = StringUtils.getRandomNumber(list.size() - 1);
            ImageLoader.load(mContext, list.get(0), ivWelcomeBg);
            //属性动画
            ivWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f)
                    .setDuration(2000).setStartDelay(100).start();
        }
    }

    @Override
    public void jumpToMain() {
        JumpUtil.go2MainActivity(mContext);
        ((WelcomeActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        /**
         * Preconditions是guava提供的用于进行代码校验的工具类，其中提供了许多重要的静态校验方法
         * 用来简化我们工作或开发中对代码的校验或预处理
         * 能够确保代码符合我们的期望，并且能够在不符合校验条件的地方，准确的为我们显示出问题所在
         * Preconditions.checkNotNull(presenter)检验对象是否为空
         */
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        mContext = null;
    }
}
