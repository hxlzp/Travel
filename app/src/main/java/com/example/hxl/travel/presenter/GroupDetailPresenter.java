package com.example.hxl.travel.presenter;

import android.content.Context;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.GroupMember;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.example.hxl.travel.model.event.GroupDissolve;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.GroupDetailContract;
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
 * Created by hxl on 2017/8/22 0022 at haichou.
 */

public class GroupDetailPresenter extends RxPresenter implements
        GroupDetailContract.Presenter {
    GroupDetailContract.View mView;
    private final Context groupDetailContext;
    private List<GroupMembers> group_members = new ArrayList<>() ;
    private String userName;
    private String groupId;
    private String userId;
    private String sessionId;

    public GroupDetailPresenter(GroupDetailContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        groupDetailContext = mView.getGroupDetailContext();
        sessionId = SharedPreferencesUtil.getSessionId(groupDetailContext);
        getData();
    }

    @Override
    public void getData() {
        groupId = mView.getGroupId();
        final String groupMemberType = mView.getGroupMemberType();
        if (group_members!=null&&group_members.size()>0){
            group_members.removeAll(group_members);
        }
        final int[] operationImg = {R.mipmap.ic_add_pic,R.mipmap.ic_group_delete_member};
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
                            if (groupMemberType.equals("1")){//成员

                            }else if (groupMemberType.equals("2")){//群主
                                group_members.add(new GroupMembers(operationImg[0],""));
                                group_members.add(new GroupMembers(operationImg[1],""));
                            }
                            mView.showData(group_members);
                        }else if (type.equals("2")){//失败

                        }
                    }
                });
        addSubscribe(subscription);
    }
    /*解散群组*/
    @Override
    public void deleteGroup() {
        groupId = mView.getGroupId();
        Subscription subscription = RetrofitHelper.groupDissolveObservable(groupId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupAdd>() {
                    @Override
                    public void onCompleted() {
                        Log.e("groupDissolve", "onCompleted: "+"completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("groupDissolve", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupAdd groupAdd) {
                        String type = groupAdd.getType();
                        //type  1  成功   2  失败
                        if (type.equals("1")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.dissolveGroupSuccess));
                            /*退出弹窗*/
                            mView.dismissPopupWindow();
                            mView.goBack();
                            EventBus.getDefault().post(new GroupDissolve());
                        }else if (type.equals("2")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.dissolveGroupFail));
                        }
                    }
                });
        addSubscribe(subscription);
    }
    /*退出群聊*/
    @Override
    public void quitGroup() {
        groupId = mView.getGroupId();
        userId = mView.getUserId();
        Subscription subscription = RetrofitHelper.groupQuitObservable(groupId, userId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupAdd>() {
                    @Override
                    public void onCompleted() {
                        Log.e("groupQuit", "onCompleted: "+"completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("groupQuit", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupAdd groupAdd) {
                        String type = groupAdd.getType();
                        //type  1  成功，退群成功，type  2  失败，用户不再该群组中 ， type  3  失败，该群不存在
                        if (type.equals("1")){
                            /*退出弹窗*/
                            mView.dismissPopupWindow();
                            //通知重新渲染页面
                            EventBus.getDefault().post(new GroupDissolve());
                            mView.goBack();
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.quitGroupSuccess));
                        }else if (type.equals("2")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.quitGroupFail));
                        }else if (type.equals("3")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.quitGroupFail));
                        }
                    }
                });
        addSubscribe(subscription);
    }

    /*邀请入群确定按钮*/
    @Override
    public void confirmInvite() {
        userName = mView.getUserName();
        groupId = mView.getGroupId();
        Subscription subscription = RetrofitHelper.groupInviteObservable(groupId, userName, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupAdd>() {
                    @Override
                    public void onCompleted() {
                        Log.e("groupInvite", "onCompleted: "+"completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("groupInvite", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupAdd groupAdd) {
                        //type  1  成功   2  失败(群不存在)
                        String type = groupAdd.getType();
                        //type  1  成功   2  失败
                        if (type.equals("1")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.InviteGroupSuccess));
                            getData();
                            /*退出对话框*/
                            mView.dismissDialog();
                            //更新群成员列表
                        }else if (type.equals("2")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.InviteGroupFail));
                        }else if (type.equals("3")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.InviteGroupNoExist));
                        } else if (type.equals("4")){
                            mView.showGroupDetailMessage(groupDetailContext.getResources().getString(R.string.InviteGroupExist));
                        }
                    }
                });
        addSubscribe(subscription);
    }
}
