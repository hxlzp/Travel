package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.SelectLocationContract;
import com.example.hxl.travel.ui.activitys.SelectLocationActivity;
import com.example.hxl.travel.ui.activitys.WebMapActivity;
import com.example.hxl.travel.ui.adapter.SortLocationAdapter;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.widget.FlowLayout;
import com.example.hxl.travel.widget.SideBar;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class SelectLocationView extends RootView<SelectLocationContract.Presenter> implements
        SelectLocationContract.View, SideBar.ISideBarSelectCallBack, View.OnClickListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.list_view_recommend)
    ListView listView ;
    @BindView(R.id.side_bar_recommend)
    SideBar sideBar ;
    private List<User> datas;
    private View headerView;
    private FlowLayout flowLayout;
    private TextView tvFlow;
    private Intent intent;

    public SelectLocationView(Context context) {
        super(context);
    }

    public SelectLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectLocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_select_location_view,this);
    }

    @Override
    protected void initView() {
        intent = new Intent();
        getHeaderView();

    }

    private void getHeaderView() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.header_location,null,false);
        flowLayout = ButterKnife.findById(headerView, R.id.fl_activity_location);
        tvFlow = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.item_location_flow,flowLayout,false);
    }

    @Override
    protected void initEvent() {
        sideBar.setOnStrSelectCallBack(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void setPresenter(SelectLocationContract.Presenter presenter) {
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
    public Context getLocationContext() {
        return mContext;
    }

    @Override
    public void showProgess() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLocationData(List<User> datas) {
        this.datas = datas;
        SortLocationAdapter adapter = new SortLocationAdapter(mContext, datas);
        listView.setAdapter(adapter);
        listView.addHeaderView(headerView);
    }
    /*显示流失布局数据*/
    @Override
    public void showFlowData(List<String> datas) {
        for(int i = 0;i<datas.size();i++){
            tvFlow.setText(datas.get(i));
            tvFlow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = tvFlow.getText().toString().trim();
                    jumpWebActivity(name);
                }
            });
            flowLayout.addView(tvFlow);
        }
    }
    private void jumpWebActivity(String title) {
        Bundle bundle = new Bundle() ;
        bundle.putString("title",title);
        intent.putExtras(bundle);
        intent.setClass(mContext,WebMapActivity.class);
        mContext.startActivity(intent);
        ((SelectLocationActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        unbinder = null;
        mContext = null;
    }

    @Override
    public void onSelectStr(int index, String selectStr) {
        for (int i = 0; i < datas.size(); i++) {
            if (selectStr.equalsIgnoreCase(datas.get(i).getFirstLetter())) {
                listView.setSelection(i); // 选择到首字母出现的位置
                return;
            }
        }
    }
    @OnClick({R.id.iv_activity_location_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_location_back:
                ((SelectLocationActivity)mContext).finish();
                ((SelectLocationActivity)mContext).overridePendingTransition(R.anim.enter_in_inactive,
                        R.anim.exit_out_active);
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int realPosition = position - 1;
    }
}
