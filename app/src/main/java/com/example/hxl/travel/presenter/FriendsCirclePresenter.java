package com.example.hxl.travel.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.FriendsCircleContract;
import com.example.hxl.travel.ui.fragments.GroupFragment;
import com.example.hxl.travel.ui.fragments.LinkerFragment;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class FriendsCirclePresenter extends RxPresenter implements FriendsCircleContract.Presenter {
    FriendsCircleContract.View mView;
    private FragmentManager childFragmentManager;
    private  Context friendContext;
    /*登陆返回Id*/
    private String userId;
    private String sessionId;
    /*存储Id*/
    private  String sessionIdS;
    private  String userIdS;
    public FriendsCirclePresenter(FriendsCircleContract.View view, FragmentManager childFragmentManager){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        friendContext = mView.getFriendContext();
        this.childFragmentManager = childFragmentManager;
        sessionIdS = SharedPreferencesUtil.getSessionId(friendContext);
        userIdS = SharedPreferencesUtil.getUserId(friendContext);
        mView.setSessionId(sessionIdS);
        getData();
    }
    @Override
    public void getData() {
        String[] tabs = friendContext.getResources().getStringArray(R.array.group);
        Fragment[] fragments = {new LinkerFragment(),new GroupFragment()};
        List<String> lists = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        if (lists!=null&lists.size()>0){
            lists.removeAll(lists);
        }
        if (fragmentList!=null){
            fragmentList.clear();
        }
        for (int i = 0 ;i<tabs.length;i++){
            lists.add(tabs[i]);
            fragmentList.add(fragments[i]);
        }
        mView.showViewPager(fragmentList,lists,childFragmentManager);
    }
}
