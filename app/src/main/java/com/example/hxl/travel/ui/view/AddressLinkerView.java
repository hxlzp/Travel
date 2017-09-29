package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.AddressLinkerContract;
import com.example.hxl.travel.ui.adapter.SortAdapter;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.widget.SideBar;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hxl on 2017/8/23 0023 at haichou.
 */

public class AddressLinkerView extends RootView<AddressLinkerContract.Presenter> implements
        AddressLinkerContract.View, SideBar.ISideBarSelectCallBack {
    @BindView(R.id.list_view_linker)
    ListView listView ;
    @BindView(R.id.side_bar_linker)
    SideBar sideBar ;

    private List<User> datas = new ArrayList<>();

    public AddressLinkerView(Context context) {
        super(context);
    }

    public AddressLinkerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressLinkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_address_linker_view,this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        sideBar.setOnStrSelectCallBack(this);
    }
    @Override
    public void setPresenter(AddressLinkerContract.Presenter presenter) {
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
    public Context getAddressLinkerContext() {
        return mContext;
    }

    @Override
    public void showData(List<User> datas) {
        this.datas = datas;
        SortAdapter adapter = new SortAdapter(mContext, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void showPregress() {

    }

    @Override
    public void hideProgress() {

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
        mContext = null;
        unbinder.unbind();
        unbinder = null;
    }
}
