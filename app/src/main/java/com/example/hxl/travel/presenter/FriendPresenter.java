package com.example.hxl.travel.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.FriendContract;
import com.example.hxl.travel.ui.fragments.GroupFragment;
import com.example.hxl.travel.ui.fragments.LinkerFragment;
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
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class FriendPresenter extends RxPresenter implements FriendContract.Presenter{
    FriendContract.View mView;
    private FragmentManager childFragmentManager;
    private final Context friendContext;
    /*登陆返回Id*/
    private String userId;
    private String sessionId;
    /*存储Id*/
    private final String sessionIdS;
    private final String userIdS;

    public FriendPresenter(FriendContract.View view, FragmentManager childFragmentManager){
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
    /*添加群组*/
    @Override
    public void addGroup() {
        userId = mView.getUserId();
        sessionId = mView.getSessionId();
        if (TextUtils.isEmpty(userId)){
            userId = userIdS;
        }else if (TextUtils.isEmpty(sessionId)){
            sessionId = sessionIdS;
        }
        /*获得用户录入的信息*/
        String groupNickName = mView.getGroupNickName();
        if (!TextUtils.isEmpty(groupNickName)){
            mView.dismissAddDialog();
        }else {
            mView.showFriendMessage(friendContext.getResources().getString(R.string.nicknameEmpty));
        }
        Subscription subscription = RetrofitHelper.groupAddObservable(userId, groupNickName,
                sessionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GroupAdd>() {
                    @Override
                    public void onCompleted() {
                        Log.e("complete", "onCompleted: "+"completed" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("fail", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupAdd groupAdd) {
                        String type = groupAdd.getType();
                        if (type.equals("1")){
                            EventBus.getDefault().post(new VisitorGroups());
                            mView.showFriendMessage(friendContext.getResources().getString(R.string.addGroupSuccess));
                        }else if (type.equals("2")){
                            mView.showFriendMessage(friendContext.getResources().getString(R.string.addGroupFail));
                        }else if (type.equals("3")){
                            mView.showFriendMessage(friendContext.getResources().getString(R.string.addGroupNoExist));
                        } else if (type.equals("4")){
                            mView.showFriendMessage(friendContext.getResources().getString(R.string.addGroupExist));
                        }
                    }
                });
        addSubscribe(subscription);
    }
}
