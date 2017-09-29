package com.example.hxl.travel.ui.fragments;


import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.FriendPresenter;
import com.example.hxl.travel.ui.view.FriendView;

import butterknife.BindView;

public class FriendFragment extends BaseFragment {
    @BindView(R.id.friend_View)
    FriendView friendView ;
    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        FragmentManager childFragmentManager = getChildFragmentManager();
        mPresenter = new FriendPresenter(friendView,childFragmentManager);
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((FriendPresenter)mPresenter).getData();
    }

}
