package com.example.hxl.travel.presenter;

import android.content.Context;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.GroupMember;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.example.hxl.travel.model.event.GroupKickMember;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.GroupDeleteMemberContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 删除群成员
 */
public class GroupDeleteMemberPresenter extends RxPresenter implements
        GroupDeleteMemberContract.Presenter{
    GroupDeleteMemberContract.View mView;
    private List<GroupMembers> group_members = new ArrayList<>() ;
    private  String groupId;
    private  Context context;
    private  String sessionId;

    public GroupDeleteMemberPresenter(GroupDeleteMemberContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        context = mView.getGroupDeleteMemberContext();
        groupId = mView.getGroupId();
        sessionId = SharedPreferencesUtil.getSessionId(context);
        getData();
    }
    @Override
    public void getData() {
        if (group_members!=null&&group_members.size()>0){
            group_members.removeAll(group_members);
        }
        Subscription subscription = RetrofitHelper.groupMemberObservable(groupId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupMember>() {
                    @Override
                    public void onCompleted() {
                        Log.e("groupMemberList", "onCompleted: "+"complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("groupMemberList", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupMember groupMember) {
                        String type = groupMember.getType();
                        if (type.equals("1")){//成功
                            group_members = groupMember.getGroup_members();
                            mView.showData(group_members);
                        }else if (type.equals("2")){//失败

                        }
                    }
                });
        addSubscribe(subscription);
    }
    /*踢出群成员*/
    @Override
    public void deleteGroupMembers(String userIds) {
        Subscription subscription = RetrofitHelper.groupDeleteObservable(groupId,
                userIds, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupAdd>() {
                    @Override
                    public void onCompleted() {
                        Log.e("deleteGroupMembers", "onCompleted: " + "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("deleteGroupMembers", "onError: " + e);
                    }

                    @Override
                    public void onNext(GroupAdd groupAdd) {
                        String type = groupAdd.getType();
                        if (type.equals("1")){
                            getData();
                            EventBus.getDefault().post(new GroupKickMember());
                            mView.showGroupDeleteMessage(context.getResources().getString(R.string.deleteGroupMemberSuccess));
                        }else if (type.equals("2")){
                            mView.showGroupDeleteMessage(context.getResources().getString(R.string.deleteGroupMemberFail));
                        }
                    }
                });
        addSubscribe(subscription);
    }
}

