package com.example.hxl.travel.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.model.event.LoginQuit;
import com.example.hxl.travel.presenter.contract.FriendContract;
import com.example.hxl.travel.ui.activitys.LoginActivity;
import com.example.hxl.travel.ui.adapter.FriendViewPagerAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class FriendView extends RootView<FriendContract.Presenter> implements
        FriendContract.View,
        TabLayout.OnTabSelectedListener, View.OnClickListener {
    @BindView(R.id.tab_fragment_friend)
    TabLayout tabLayout ;
    @BindView(R.id.viewpager_fragment_friend)
    ViewPager viewPager ;

    /*加入群组*/
    private EditText editAddName;
    private Button btnAddGroupCancel;
    private Button btnAddGroupConfirm;
    private AlertDialog.Builder addGroupBuilder;
    private View addGroupView;
    private AlertDialog addGroupDialogShow;

    /*登陆 String sessionId*/
    private String sessionId = null;
    private String userId = null;
    /*存储 sessionId*/
    private String sessionIdS = null;

    private String userName;
    private String userPhone;

    private int position;

    public FriendView(Context context) {
        super(context);
    }

    public FriendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FriendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_friend_view,this);
    }

    @Override
    protected void initView() {
        initTabLayout();
        initDialog();
    }
    /*初始化对话框*/
    private void initDialog() {
        //添加群
        addGroupView = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_group,null);
        addGroupBuilder = new AlertDialog.Builder(mContext);
        addGroupBuilder.setView(addGroupView);
        addGroupBuilder.setCancelable(false);
        editAddName = ButterKnife.findById(addGroupView,
                R.id.edit_dialog_add_group_name_edit);
        btnAddGroupCancel = ButterKnife.findById(addGroupView,
                R.id.btn_dialog_add_friend_cancel);
        btnAddGroupConfirm = ButterKnife.findById(addGroupView,
                R.id.btn_dialog_add_friend_confirm);
    }
    /*初始化Tab*/
    private void initTabLayout() {
        //设置模式
        tabLayout.setTabGravity(Gravity.CENTER_HORIZONTAL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
    @Override
    protected void initEvent() {
        tabLayout.addOnTabSelectedListener(this);
        btnAddGroupCancel.setOnClickListener(this);
        btnAddGroupConfirm.setOnClickListener(this);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showData() {

    }

    @Override
    public Context getFriendContext() {
        return mContext;
    }
    /*添加群组时获得群昵称*/
    @Override
    public String getGroupNickName() {
        String nickName = editAddName.getText().toString().trim();
        return nickName;
    }
    /*邀请入群时，获得的群组昵称*/
    @Override
    public String getGroupInviteNickName() {

        return null;
    }

    /*邀请入群时，获得的用户名*/
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
    public void showViewPager(List<Fragment> fragments, List<String> tab,
                              FragmentManager childFragmentManager) {
        for (int i = 0;i<tab.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(tab.get(i)));
        }
        FriendViewPagerAdapter adapter =
                new FriendViewPagerAdapter(childFragmentManager,fragments,tab);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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

    @Override
    public void dismissAddDialog() {
        addGroupDialogShow.cancel();
        if (addGroupView.getParent()!=null){
            ((ViewGroup)addGroupView.getParent()).removeAllViews();
        }
    }


    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }
    /*tab监听*/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        this.position = position;
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
        userName = loginEvent.getUserName();
        userPhone = loginEvent.getUserPhone();
        this.sessionId = sessionId;
        userId = user_id;
    }
    /*event退出登陆事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLoginQuit(LoginQuit loginQuit){
        userName = loginQuit.getUserName();
        userPhone = loginQuit.getUsePhone();
        sessionIdS = null;
    }

    /*点击事件*/
    @OnClick({R.id.iv_fragment_friend_add})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_fragment_friend_add://添加群组
                Log.e("userPhone", "onClick: "+userPhone);
                Log.e("userName", "onClick: "+userName);
                Log.e("sessionIdS", "onClick: "+sessionIdS);
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    showAdd();
                }else {
                    JumpUtil.jump(mContext, LoginActivity.class);
                }
                break;
            case R.id.btn_dialog_add_friend_cancel://添加群取消
                cancelAddGroup();
                break;
            case R.id.btn_dialog_add_friend_confirm://添加群确定
                mPresenter.addGroup();
                break;
        }
    }


    /*取消加群*/
    private void cancelAddGroup() {
        dismissAddDialog();
    }

    /*加入群聊*/
    private void showAdd() {
        switch (position){
            case 0://添加联系人

                break;
            case 1://添加群组
                addGroupDialogShow = addGroupBuilder.show();
                break;
        }

    }
}
