package com.example.hxl.travel.presenter;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.GroupChatContract;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 聊天
 */
public class GroupChatPresenter extends RxPresenter implements GroupChatContract.Presenter {
    GroupChatContract.View mView;
    public GroupChatPresenter(GroupChatContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getData();
    }
    @Override
    public void getData() {
        mView.showData();
    }
}
