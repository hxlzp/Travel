package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.ScannerGroupMembers;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.presenter.contract.ScannerContract;
import com.example.hxl.travel.ui.activitys.ScannerActivity;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;
import com.google.zxing.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hxl on 2017/6/13 at haiChou.
 */
public class ScannerView extends RootView<ScannerContract.Presenter> implements
        ScannerContract.View, ZXingScannerView.ResultHandler,View.OnClickListener {
    @BindView(R.id.scanner)
    ZXingScannerView zXingScannerView ;
    @BindView(R.id.web)
    WebView webView ;
    @BindView(R.id.rl_scanner)
    RelativeLayout relativeLayout;
    @BindView(R.id.btn_activity_scanner_name)
    Button btnName;
    @BindView(R.id.btn_activity_scanner_count)
    Button btnCount;
    private String group_nickname;
    /*登陆 String sessionId*/
    private String sessionId = null;
    private String userId = null;
    /*存储 sessionId*/
    private String sessionIdS = null;

    public ScannerView(Context context) {
        super(context);
    }

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext,R.layout.activity_scanner_view,this);
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
    public void showData() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getScannerContext() {
        return mContext;
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public void showScannerMessage(List<ScannerGroupMembers> group_members) {
        if (group_members!=null&&group_members.size()>0){
            group_nickname = group_members.get(0).getGroup_nickname();
            int count = group_members.size();
            btnName.setText(group_nickname);
            btnCount.setText(mContext.getResources().getString(R.string.scannerCommon)+count+mContext.getResources().getString(R.string.scannerPerson));

        }
    }

    @Override
    public String getGroupName() {
        return group_nickname;
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
    public void setPresenter(ScannerContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /*设置处理结果回调*/
        zXingScannerView.setResultHandler(this);
        /*打开摄像头*/
        zXingScannerView.startCamera();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /*关闭摄像头*/
        zXingScannerView.stopCamera();
        mContext = null;
        unbinder.unbind();
        unbinder = null;
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.iv_activity_scanner_back,R.id.btn_activity_scanner_commit})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_scanner_back://返回
                back();
            break;
            case R.id.btn_activity_scanner_commit://确定
                mPresenter.confirm();
                break;
        }
    }

    public void back() {
        ((ScannerActivity)mContext).finish();
    }

    /*实现接口回调，将数据回传*/
    @Override
    public void handleResult(Result result) {
        String text = result.getText();
        if (text.contains("html/visitorGroup/findAllMemberByGroupId.do?group_id")){
            webView.setVisibility(View.GONE);
            zXingScannerView.setVisibility(GONE);
            relativeLayout.setVisibility(VISIBLE);
            mPresenter.scanner(text);
        }else {
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(GONE);
            webView.loadUrl(text); // 将识别的内容当作网址加载到WebView
        }
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
