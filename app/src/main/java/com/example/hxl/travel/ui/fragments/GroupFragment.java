package com.example.hxl.travel.ui.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.GroupPresenter;
import com.example.hxl.travel.ui.view.GroupView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseFragment {
    @BindView(R.id.group_view)
    GroupView groupView ;
    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_group;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new GroupPresenter(groupView);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((GroupPresenter)mPresenter).getData();
    }
}
