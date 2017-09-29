package com.example.hxl.travel.ui.fragments;


import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.RecommendPresenter;
import com.example.hxl.travel.ui.view.RecommendView;

import butterknife.BindView;

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.recommend_view)
    RecommendView recommendView;

    @Override
    protected int getLayout() {

        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new RecommendPresenter(recommendView);
    }

    /**
     * 懒加载数据
     */
    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((RecommendPresenter) mPresenter).onRefresh();
    }
}
