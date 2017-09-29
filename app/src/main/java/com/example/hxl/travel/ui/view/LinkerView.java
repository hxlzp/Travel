package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.LinkerContract;
import com.example.hxl.travel.ui.activitys.AddressLinkerActivity;
import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.adapter.SortAdapter;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.widget.SideBar;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hxl on 2017/8/11 0011 at haichou.
 */

public class LinkerView extends RootView<LinkerContract.Presenter> implements
        LinkerContract.View, SideBar.ISideBarSelectCallBack ,
        View.OnClickListener{
    ListView listView ;
    @BindView(R.id.side_bar_linker)
    SideBar sideBar ;
    private List<User> datas = new ArrayList<>();
    private RelativeLayout rlLinker;

    public LinkerView(Context context) {
        super(context);
    }

    public LinkerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_linker_view,this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view_linker);
        rlLinker = (RelativeLayout) findViewById(R.id.rl_linker);
    }

    @Override
    protected void initEvent() {
        sideBar.setOnStrSelectCallBack(this);
        rlLinker.setOnClickListener(this);
    }

    @Override
    public void setPresenter(LinkerContract.Presenter presenter) {
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
    public void showData(List<User> datas) {
        this.datas = datas;
        SortAdapter adapter = new SortAdapter(mContext, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public Context getLinkerContext() {
        return mContext;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_linker:
                JumpUtil.jump(mContext, AddressLinkerActivity.class);
                ((MainActivity)mContext).overridePendingTransition(R.anim.enter_in_active,
                        R.anim.exit_out_inactive);
                break;
        }
    }

    /*SideBar监听*/
    @Override
    public void onSelectStr(int index, String selectStr) {
        for (int i = 0; i < datas.size(); i++) {
            if (selectStr.equalsIgnoreCase(datas.get(i).getFirstLetter())) {
                listView.setSelection(i); // 选择到首字母出现的位置
                return;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        unbinder = null;
    }
}
