package com.example.hxl.travel.ui.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.FriendsCirclePresenter;
import com.example.hxl.travel.ui.view.FriendsCircleView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsCircleFragment extends BaseFragment {
    @BindView(R.id.friend_circle_view)
    FriendsCircleView friendsCircleView ;

    public FriendsCircleFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new FriendsCirclePresenter(friendsCircleView,getChildFragmentManager());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_friends_circle;
    }
    /*懒加载数据*/
    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((FriendsCirclePresenter)mPresenter).getData();
    }
}
