package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.presenter.contract.FriendsCircleContract;
import com.example.hxl.travel.ui.adapter.FriendViewPagerAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class FriendsCircleView extends RootView<FriendsCircleContract.Presenter>
        implements FriendsCircleContract.View, TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_fragment_friend)
    TabLayout tabLayout ;
    @BindView(R.id.viewpager_fragment_friend)
    ViewPager viewPager ;
    /*登陆 String sessionId*/
    private String sessionId = null;
    private String userId = null;
    /*存储 sessionId*/
    private String sessionIdS = null;
    public FriendsCircleView(Context context) {
        super(context);
    }

    public FriendsCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FriendsCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_friends_circle_view,this);
    }

    @Override
    protected void initView() {
        initTabLayout();
    }

    @Override
    protected void initEvent() {
        tabLayout.addOnTabSelectedListener(this);
    }
    /*初始化Tab*/
    private void initTabLayout() {
        //设置模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void setPresenter(FriendsCircleContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getFriendContext() {
        return mContext;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setSessionId(String sessionId) {
        sessionIdS = sessionId;
    }

    @Override
    public void showViewPager(List<Fragment> fragments, List<String> tab, FragmentManager childFragmentManager) {
        for (int i = 0;i<tab.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(tab.get(i)));
        }
        FriendViewPagerAdapter adapter =
                new FriendViewPagerAdapter(childFragmentManager,fragments,tab);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void showData() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showFriendMessage(String message) {
        EventUtil.showToast(mContext,message);
    }

    /*tab监听*/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        unbinder = null;
        mContext = null;
        EventBus.getDefault().unregister(this);
    }
    /*登陆事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLogin(LoginEvent loginEvent ){
        String sessionId = loginEvent.getSessionId();
        String user_id = loginEvent.getUser_id();
        this.sessionId = sessionId;
        userId = user_id;
    }
}
