package com.example.hxl.travel.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.model.event.LoginQuit;
import com.example.hxl.travel.presenter.contract.MineContract;
import com.example.hxl.travel.ui.activitys.FootprintActivity;
import com.example.hxl.travel.ui.activitys.LoginActivity;
import com.example.hxl.travel.ui.activitys.SettingsActivity;
import com.example.hxl.travel.ui.adapter.MineRecyclerAdapter;
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
 * Created by hxl on 2017/1/3 at haiChou.
 */
public class MineView extends RootView<MineContract.Presenter> implements MineContract.View
,View.OnClickListener, MineRecyclerAdapter.OnItemClickListener {
    @BindView(R.id.rv_fragment_mine)
    RecyclerView recyclerView ;
    @BindView(R.id.btn_fragment_mine_name)
    TextView btnLogin;
    @BindView(R.id.btn_fragment_mine_nickname)
    TextView btnNickname;
    @BindView(R.id.not_login)
    RelativeLayout rlNotLogin;
    @BindView(R.id.complete_login)
    RelativeLayout rlCompleteLogin;
    private String userName;
    private String userPhone;
    private Intent intent;

    private LayoutInflater layoutInflater;

    private String sessionId;

    /*登录对话框*/
    private AlertDialog alertDialog;


    public MineView(Context context) {
        super(context);
    }

    public MineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 加载布局
     */
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_mine_view,this);
    }

    @Override
    protected void initView() {

        layoutInflater = LayoutInflater.from(mContext);

        intent = new Intent();

        //初始化RecyclerView
        //创建线性管理器对象
        LinearLayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,false);
        //设置管理器对象
        recyclerView.setLayoutManager(manager);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    /*登录对话框*/
    private void showLoginDialog() {
        View view = layoutInflater.inflate(R.layout.dialog_login_whether,null,false);
        Button btnDialogCancel = ButterKnife.findById(view,R.id.btn_dialog_cancel);
        Button btnDialogConfirm = ButterKnife.findById(view,R.id.btn_dialog_confirm);
        btnDialogCancel.setOnClickListener(this);
        btnDialogConfirm.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        //显示对话框
        alertDialog = builder.show();
    }
    @Override
    protected void initEvent() {

    }
    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getMineContext() {
        return mContext;
    }

    @Override
    public void showContent(List<Integer> data, List<String> title) {
        MineRecyclerAdapter adapter = new MineRecyclerAdapter(data,title,mContext);
        //设置适配器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showUserMessage(String phone,String nickname) {
        if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(nickname)){
            rlNotLogin.setVisibility(GONE);
            rlCompleteLogin.setVisibility(VISIBLE);
            btnNickname.setText(nickname);
        }else {
            rlNotLogin.setVisibility(VISIBLE);
            rlCompleteLogin.setVisibility(GONE);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    /**
     * Button点击按钮
     */
    @OnClick({R.id.btn_fragment_mine_name,R.id.iv_fragment_mine_setting})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_fragment_mine_name:
                JumpUtil.jump(mContext,LoginActivity.class);
            break;
            case R.id.iv_fragment_mine_setting:
                JumpUtil.jump(mContext,SettingsActivity.class);
                break;

            case R.id.btn_dialog_cancel://对话框取消
                alertDialog.cancel();
                break;
            case R.id.btn_dialog_confirm://对话框确定
                alertDialog.cancel();
                JumpUtil.jump(mContext, LoginActivity.class);
                break;
        }

    }
    /*event事件登陆状态*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLogin(LoginEvent loginEvent ){
        userName = loginEvent.getUserName();
        userPhone = loginEvent.getUserPhone();
        if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)){
            rlNotLogin.setVisibility(GONE);
            rlCompleteLogin.setVisibility(VISIBLE);
            btnNickname.setText(userName);
        }else {
            rlNotLogin.setVisibility(VISIBLE);
            rlCompleteLogin.setVisibility(GONE);
        }

    }
    /*event退出登陆事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLoginQuit(LoginQuit loginQuit){
        btnLogin.setText(mContext.getResources().getString(R.string.loginExperienceMore));
        rlNotLogin.setVisibility(VISIBLE);
        rlCompleteLogin.setVisibility(GONE);

        userName = loginQuit.getUserName();
        userPhone = loginQuit.getUsePhone();
        sessionId = null;
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
        mContext = null;
        EventBus.getDefault().unregister(this);
    }
    /*recycler点击事件*/
    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)
                        || !TextUtils.isEmpty(sessionId)){
                    intent.setClass(mContext, FootprintActivity.class);
                    mContext.startActivity(intent);
                }else {
                    showLoginDialog();
                }
                break;
        }
    }
}
