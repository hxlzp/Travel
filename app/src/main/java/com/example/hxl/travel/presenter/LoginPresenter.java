package com.example.hxl.travel.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.Login;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.LoginContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class LoginPresenter extends RxPresenter implements LoginContract.Presenter{
    LoginContract.View mView ;
    private final Context loginContext;
    private Subscription subscription;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor edit;

    public LoginPresenter(LoginContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        loginContext = mView.getLoginContext();
        sharedPreferences = SharedPreferencesUtil.getSharedPreferences(loginContext, "user");
        edit = sharedPreferences.edit();
        String userPhone = sharedPreferences.getString("userPhone", null);
        mView.showUserMessage(userPhone);
    }

    @Override
    public void login() {
        //获得用户录入的信息
        final String userPhone = mView.getUserAccount();
        final String userPassword = mView.getUserPassword();
        if (TextUtils.isEmpty(userPhone)){
            mView.showLoginMassage(loginContext.getString(R.string.accountEmpty));
        } else if (TextUtils.isEmpty(userPassword)){
            mView.showLoginMassage(loginContext.getString(R.string.passwordEmpty));
        } else {
            subscription = RetrofitHelper.loginObservable(userPhone, userPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Login>() {
                        @Override
                        public void onCompleted() {
                            Log.e("login", "onCompleted: "+"completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("login", "onError: "+e);
                        }

                        @Override
                        public void onNext(Login login) {
                            String type = login.getType();
                            String sessionId = login.getSessionId();
                            String nickname = login.getNickname();
                            String user_id = login.getUser_id();
                            if (type.equals("1")){
                               /*存储用户数据*/
                                edit.putString("userPhone",userPhone);
                                edit.putString("userPwd",userPassword);
                                edit.putString("nickname",nickname);
                                edit.putString("sessionId",sessionId);
                                edit.putString("user_id",user_id);
                                //提交
                                edit.commit();
                                mView.showLoginMassage(loginContext.getString(R.string.loginSuccess));
                                EventBus.getDefault().post(new LoginEvent(userPhone,
                                        nickname,sessionId,user_id));
                                mView.backPage();
                            }else if (type.equals("2")){
                                mView.showLoginMassage(loginContext.getString(R.string.loginFail));
                            }else {
                                mView.showLoginMassage(loginContext.getString(R.string.networkFail));
                            }
                        }
                    });
            addSubscribe(subscription);
        }
    }
}
