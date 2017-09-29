package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.RegisterContract;
import com.example.hxl.travel.ui.activitys.RegisterActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class RegisterView extends RootView<RegisterContract.Presenter>
        implements RegisterContract.View ,View.OnClickListener{
    @BindView(R.id.et_activity_register_phone)
    EditText editPhone;
    @BindView(R.id.et_activity_register_pwd)
    EditText editPassword;
    public RegisterView(Context context) {
        super(context);
    }

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegisterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 加载布局
     */
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_register_view,this);
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

    @Override
    public Context getLoginContext() {
        return mContext;
    }

    @Override
    public String getUserName() {
        return  null;
    }

    @Override
    public String getUserPhone() {
        return editPhone.getText().toString().trim();
    }

    @Override
    public String getUserCode() {
        return null;
    }

    @Override
    public String getUserPassword() {
        return editPassword.getText().toString().trim();
    }

    @Override
    public void backPage() {
        ((RegisterActivity)mContext).finish();
    }

    @Override
    public void showRegisterMassage(String massage) {
        EventUtil.showToast(mContext,massage);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    /**
     * Button点击事件
     */
    @OnClick({R.id.iv_activity_register_back,R.id.tv_activity_register})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_register_back://返回
                ((RegisterActivity)mContext).finish();
            break;
            case R.id.tv_activity_register://注册
                mPresenter.register();
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        mContext = null;
    }
}
