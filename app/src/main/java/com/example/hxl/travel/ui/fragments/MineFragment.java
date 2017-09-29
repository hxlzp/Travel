package com.example.hxl.travel.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.MinePresenter;
import com.example.hxl.travel.ui.view.MineView;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_View)
    MineView mineView ;
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new MinePresenter(mineView);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((MinePresenter)mPresenter).getData();
    }
}
