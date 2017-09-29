package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.ScenicDetailsContract;
import com.example.hxl.travel.ui.activitys.ScenicDetailsActivity;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import butterknife.BindView;

/**
 * Created by hxl on 2017/8/21 0021 at haichou.
 */

public class ScenicDetailsView extends RootView<ScenicDetailsContract.Presenter> implements
        ScenicDetailsContract.View, View.OnClickListener {
    @BindView(R.id.tv_activity_scenic_details)
    TextView tvScenicDetails;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_activity_scenic_title)
    TextView tvScenicTitle;

    private int toolColor = 0x88000000;
    public ScenicDetailsView(Context context) {
        super(context);
    }

    public ScenicDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScenicDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_scenic_details_view,this);
    }

    @Override
    protected void initView() {
        Intent intent = ((ScenicDetailsActivity)mContext).getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(toolColor);
        toolbar.setNavigationIcon(R.mipmap.ic_backalpha);

        tvScenicTitle.setText(title);
    }

    @Override
    protected void initEvent() {
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getScenicContext() {
        return mContext;
    }

    @Override
    public void showData(String datas) {
        tvScenicDetails.setText("\u3000\u3000" + datas + datas);
    }
    @Override
    public void setPresenter(ScenicDetailsContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public void onClick(View v) {
        ((ScenicDetailsActivity)mContext).finish();
    }
}

