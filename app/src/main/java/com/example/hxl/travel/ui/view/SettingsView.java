package com.example.hxl.travel.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.model.event.LoginQuit;
import com.example.hxl.travel.presenter.contract.SettingsContract;
import com.example.hxl.travel.ui.activitys.AboutActivity;
import com.example.hxl.travel.ui.activitys.LoginActivity;
import com.example.hxl.travel.ui.activitys.PersonInfoActivity;
import com.example.hxl.travel.ui.activitys.SettingsActivity;
import com.example.hxl.travel.utils.DataCleanManager;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class SettingsView extends RootView<SettingsContract.Presenter> implements
        SettingsContract.View, View.OnClickListener{
    @BindView(R.id.btn_activity_settings_clear_cache)
    Button clearCache;
    @BindView(R.id.tv_activity_settings_quit)
    TextView tvQuit;
    private Button btnCancel;
    private Button btnConfirm;
    private AlertDialog dialog;
    private Button btnQuitCancel;
    private Button btnQuitConfirm;
    private AlertDialog quitLoginDialog;
    private String sessionId;

    public SettingsView(Context context) {
        super(context);
    }

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_settings_view,this);
    }

    @Override
    protected void initView() {
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(mContext);
            clearCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Context getSettingsContext() {
        return mContext;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    /**
     * Button按钮点击事件
     */
    @OnClick({R.id.iv_activity_setting_back,R.id.btn_activity_settings_person,
            R.id.btn_activity_settings_clear_cache,
            R.id.btn_activity_settings_about_versions,R.id.tv_activity_settings_quit})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_setting_back://退出
                ((SettingsActivity)mContext).finish();
                break;
            case R.id.btn_activity_settings_person://个人资料
                JumpUtil.jump(mContext,PersonInfoActivity.class);
            break;
            case R.id.btn_activity_settings_clear_cache://清除缓存
                showClearCacheDialog();
                break;
            case R.id.btn_activity_settings_about_versions://关于
                JumpUtil.jump(mContext,AboutActivity.class);
                break;
            case R.id.tv_activity_settings_quit://退出登陆
                showQuitDialog();
            break;
            case R.id.btn_dialog_clear_cache_cancel://取消对话框
                dialog.dismiss();
                break;
            case R.id.btn_dialog_clear_cache_confirm://确认对话框
                DataCleanManager.clearAllCache(mContext);
                try {
                    clearCache.setText(DataCleanManager.getTotalCacheSize(mContext));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                break;
            case R.id.btn_dialog_exit_log_cancel://取消取消登陆对话框
                quitLoginDialog.dismiss();
                break;
            case R.id.btn_dialog_exit_log_confirm://确认取消登陆对话框
                SharedPreferences sharedPreferences = SharedPreferencesUtil
                        .getSharedPreferences(mContext, "user");
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.clear();
                edit.commit();
                tvQuit.setClickable(false);
                String userName = null;
                String userPhone = null;
                EventBus.getDefault().post(new LoginQuit(userName,userPhone));
                JumpUtil.jump(mContext, LoginActivity.class);
                quitLoginDialog.dismiss();
                break;

        }
    }
    /*登陆成功事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLoginMessage(LoginEvent loginEvent){
        String sessionId = loginEvent.getSessionId();
        if (sessionId!=null){
            tvQuit.setClickable(true);
        }

    }
    private void showQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //加载布局页面
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_exit_login, null);
        btnQuitCancel = ButterKnife.findById(view, R.id.btn_dialog_exit_log_cancel);
        btnQuitConfirm = ButterKnife.findById(view, R.id.btn_dialog_exit_log_confirm);
        btnQuitCancel.setOnClickListener(this);
        btnQuitConfirm.setOnClickListener(this);
        builder.setView(view);
        //显示对话框
        quitLoginDialog = builder.show();
    }

    private void showClearCacheDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //加载布局页面
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_clear_cache, null);
        btnCancel = ButterKnife.findById(view, R.id.btn_dialog_clear_cache_cancel);
        btnConfirm = ButterKnife.findById(view, R.id.btn_dialog_clear_cache_confirm);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        builder.setView(view);
        //显示对话框
        dialog = builder.show();
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
