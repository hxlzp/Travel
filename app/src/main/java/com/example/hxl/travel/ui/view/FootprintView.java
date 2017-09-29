package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.FootprintScenicList;
import com.example.hxl.travel.presenter.contract.FootprintContract;
import com.example.hxl.travel.ui.activitys.FootprintActivity;
import com.example.hxl.travel.ui.adapter.FootprintRecyclerViewAdapter;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/8/15 0015 at haichou.
 */

public class FootprintView extends RootView<FootprintContract.Presenter> implements
        FootprintContract.View, FootprintRecyclerViewAdapter.OnAdapterClickListener,
        View.OnClickListener{
    @BindView(R.id.rv_fontprint)
    RecyclerView recyclerView ;
    public FootprintView(Context context) {
        super(context);
    }

    public FootprintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FootprintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext,R.layout.activity_footprint_view,this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,false) ;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void initEvent() {

    }

    @Override
    public void setPresenter(FootprintContract.Presenter presenter) {
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
    public Context getFootprintContext() {
        return mContext;
    }

    @Override
    public void showWebData(List<String> urlData, List<FootprintScenicList> scenicListDatas) {
        FootprintRecyclerViewAdapter adapter = new
                FootprintRecyclerViewAdapter(mContext,urlData,scenicListDatas) ;
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterClickListener(this);
    }
    /*recyclerView点击事件*/
    @Override
    public void onClick(List<FootprintScenicList> datas, int position) {
        Log.e(">>>>>", "onClick: "+datas.get(position).getTitle());
    }
    @OnClick({R.id.iv_activity_footprint_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_footprint_back:
                ((FootprintActivity) mContext).finish();
                ((FootprintActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
        }
    }
}
