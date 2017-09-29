package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.RecommendList;
import com.example.hxl.travel.presenter.contract.MoreScenicContract;
import com.example.hxl.travel.ui.activitys.MoreScenicActivity;
import com.example.hxl.travel.ui.activitys.ScenicDetailsActivity;
import com.example.hxl.travel.ui.adapter.MoreScenicAdapter;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.utils.springutil.IconHeader;
import com.example.hxl.travel.widget.SpringView;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/8/14 0014 at haichou.
 */

public class MoreScenicView extends RootView<MoreScenicContract.Presenter> implements
        MoreScenicContract.View, View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener, SpringView.OnFreshListener {
    @BindView(R.id.sv_more_scenic)
    SpringView springView ;
    @BindView(R.id.rl_more_scenic)
    RecyclerView recyclerView ;
    private List<RecommendList> datas;
    private Intent intent;

    private int[] refreshAnimSrcs = new int[]{R.mipmap.ptr_1,R.mipmap.ptr_2,
            R.mipmap.ptr_3,R.mipmap.ptr_4,R.mipmap.ptr_5,
            R.mipmap.ptr_6};

    public MoreScenicView(Context context) {
        super(context);
    }

    public MoreScenicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreScenicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext,R.layout.activity_more_scenic_view,this);
    }

    @Override
    protected void initView() {
        intent = new Intent();
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false) ;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        springView.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initEvent() {
        springView.setListener(this);
        springView.setHeader(new IconHeader(mContext,null,refreshAnimSrcs));
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
    public Context getScenicContent() {
        return mContext;
    }

    @Override
    public void showData(List<RecommendList> datas) {
        this.datas = datas;
        MoreScenicAdapter moreScenicAdapter = new MoreScenicAdapter(datas,mContext) ;
        recyclerView.setAdapter(moreScenicAdapter);
        moreScenicAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
    @Override
    public void setPresenter(MoreScenicContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext = null;
        unbinder.unbind();
        unbinder = null;
    }
    /*点击事件*/
    @OnClick({R.id.iv_activity_scenic_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_scenic_back:
                ((MoreScenicActivity)mContext).finish();
            break;
        }
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        RecommendList recommendList = (RecommendList) data;
        String title = recommendList.getTitle();
        jumpWebActivity(title);

    }

    private void jumpWebActivity(String title) {
        Bundle bundle = new Bundle() ;
        bundle.putString("title",title);
        intent.putExtras(bundle);
        intent.setClass(mContext,ScenicDetailsActivity.class);
        mContext.startActivity(intent);
    }

    /*SpringView加载刷新监听*/
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }
}
