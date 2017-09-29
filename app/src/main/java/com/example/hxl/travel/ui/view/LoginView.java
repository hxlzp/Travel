package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.LoginContract;
import com.example.hxl.travel.ui.activitys.LoginActivity;
import com.example.hxl.travel.ui.activitys.RegisterActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class LoginView extends RootView<LoginContract.Presenter> implements
        LoginContract.View ,View.OnClickListener {
    @BindView(R.id.et_activity_login_account)
    EditText editAccount;
    @BindView(R.id.et_activity_login_password)
    EditText editPassword;
    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginView(Context context) {
        super(context);
    }

    /**
     * 加载布局页面
     */
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_login_view,this);
    }

    public Context getLoginContext(){
        return mContext;
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    /**
     * 获得用户账号
     */
    @Override
    public String getUserAccount() {
        String account = editAccount.getText().toString().trim();
        return account;
    }
    /**
     * 获得用户密码
     */
    @Override
    public String getUserPassword() {
        String password = editPassword.getText().toString().trim();
        return password;
    }
    /**
     * 登陆成功返回页面
     */
    @Override
    public void backPage() {
        ((LoginActivity)mContext).finish();
    }
    /**
     * 登陆失败提示用户(用户名、密码错误或为空等)
     */
    @Override
    public void showLoginMassage(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    public void showUserMessage(String phone) {
        if (!TextUtils.isEmpty(phone)){
            editAccount.setText(phone);
        }
    }

    /**
     * 登陆时显示进度条
     */
    @Override
    public void showProgress() {

    }
    /**
     * 登陆成功时，隐藏进度条
     */
    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        mContext = null;
    }

    /**
     * button按钮点击事件
     */
    @OnClick({R.id.iv_activity_login_back,R.id.btn_activity_login_register,
            R.id.tv_activity_login,R.id.iv_activity_login_wechat})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_login_back://返回
                ((LoginActivity)mContext).finish();
            break;
            case R.id.btn_activity_login_register://注册
                JumpUtil.jump(mContext,RegisterActivity.class);
                break;
            case R.id.tv_activity_login://登陆
                mPresenter.login();
                break;
            case R.id.iv_activity_login_wechat://微信登陆

                break;


        }
    }

}
