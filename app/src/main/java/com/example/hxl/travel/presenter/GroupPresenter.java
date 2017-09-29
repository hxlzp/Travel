package com.example.hxl.travel.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.GroupList;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.GroupContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 群组
 */
public class GroupPresenter extends RxPresenter implements GroupContract.Presenter {
    GroupContract.View mView;
    private  Context groupContext;
    private  String userId;
    private  String sessionId;

    public GroupPresenter( GroupContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        groupContext = mView.getGroupContext();
//        userId = SharedPreferencesUtil.getUserId(groupContext);
//        sessionId = SharedPreferencesUtil.getSessionId(groupContext);
        getData();
    }
    @Override
    public void getData() {
        userId = mView.getUserId();
        sessionId = mView.getSessionId();
        if (TextUtils.isEmpty(userId)){
            userId = SharedPreferencesUtil.getUserId(groupContext);
        }else if (TextUtils.isEmpty(sessionId)){
            sessionId = SharedPreferencesUtil.getSessionId(groupContext);
        }
        Subscription subscription = RetrofitHelper.groupListObservable(userId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupList>() {
                    @Override
                    public void onCompleted() {
                        Log.e("complete", "onCompleted: "+"completed" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("fail", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupList groupList) {
                        String type = groupList.getType();
                        if (type.equals("1")){
                            List<VisitorGroups> visitorGroups = groupList.getVisitorGroups();
                            mView.showData(visitorGroups);
                        }else if (type.equals("2")){

                        }
                    }
                });
        addSubscribe(subscription);
    }
    /*解散群组*/
    @Override
    public void dissolveGroup(String groupId) {
        sessionId = mView.getSessionId();
        if (TextUtils.isEmpty(sessionId)){
            sessionId = SharedPreferencesUtil.getSessionId(groupContext);
        }
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
                            //getData();
                            mView.showGroupMessage(groupContext.getResources().getString(R.string.dissolveGroupSuccess));
                        }else if (type.equals("2")){
                            mView.showGroupMessage(groupContext.getResources().getString(R.string.dissolveGroupFail));
                        }
                    }
                });
        addSubscribe(subscription);
    }
    /*退出群组*/
    @Override
    public void quitGroup(String userId,String groupId) {
        sessionId = mView.getSessionId();
        if (TextUtils.isEmpty(sessionId)){
            sessionId = SharedPreferencesUtil.getSessionId(groupContext);
        }
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
                            //getData();
                            mView.showGroupMessage(groupContext.getResources().getString(R.string.quitGroupSuccess));
                        }else if (type.equals("2")){
                            mView.showGroupMessage(groupContext.getResources().getString(R.string.quitGroupFail));
                        }else if (type.equals("3")){
                            mView.showGroupMessage(groupContext.getResources().getString(R.string.quitGroupFail));
                        }
                    }
                });
        addSubscribe(subscription);
    }
}
