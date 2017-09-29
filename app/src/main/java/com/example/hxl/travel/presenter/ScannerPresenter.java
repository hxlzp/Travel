package com.example.hxl.travel.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.ScannerGroup;
import com.example.hxl.travel.model.bean.ScannerGroupMembers;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.ScannerContract;
import com.example.hxl.travel.ui.activitys.ScannerActivity;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/6/13 at haiChou.
 */
public class ScannerPresenter extends RxPresenter implements ScannerContract.Presenter{
    ScannerContract.View mView;
    private final Context context;
    /*登陆返回Id*/
    private String userId;
    private String sessionId;
    /*存储Id*/
    private final String sessionIdS;
    private final String userIdS;

    public ScannerPresenter(ScannerContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        context = mView.getScannerContext();
        sessionIdS = SharedPreferencesUtil.getSessionId(context);
        userIdS = SharedPreferencesUtil.getUserId(context);
        mView.setSessionId(sessionIdS);
        getData();
    }
    @Override
    public void getData() {

    }
    /*扫码*/
    @Override
    public void scanner(final String url) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("scannerGroupMembers", "onFailure: "+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            final String string = response.body().string();
                            ((ScannerActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    ScannerGroup scannerGroup =
                                            gson.fromJson(string, ScannerGroup.class);
                                    String type = scannerGroup.getType();
                                    if (type.equals("1")){
                                        List<ScannerGroupMembers> group_members =
                                                scannerGroup.getGroup_members();
                                        mView.showScannerMessage(group_members);
                                    }
                                }
                            });

                        }
                    }
                });
            }
        }.start();
    }
    /*确定加好友*/
    @Override
    public void confirm() {
        userId = mView.getUserId();
        sessionId = mView.getSessionId();
        if (TextUtils.isEmpty(userId)){
            userId = userIdS;
        }else if (TextUtils.isEmpty(sessionId)){
            sessionId = sessionIdS;
        }
        /*获得用户录入的信息*/
        String groupNickName = mView.getGroupName();
        Subscription subscription =
                RetrofitHelper.groupAddObservable(userId, groupNickName, sessionId)
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
                            mView.back();
                            mView.showMessage(context.getResources().getString(R.string.addGroupSuccess));
                        }else if (type.equals("2")){
                            mView.showMessage(context.getResources().getString(R.string.addGroupFail));
                        }else if (type.equals("3")){
                            mView.showMessage(context.getResources().getString(R.string.addGroupNoExist));
                        } else if (type.equals("4")){
                            mView.showMessage(context.getResources().getString(R.string.addGroupExist));
                        }
                    }
                });
        addSubscribe(subscription);

    }
}
