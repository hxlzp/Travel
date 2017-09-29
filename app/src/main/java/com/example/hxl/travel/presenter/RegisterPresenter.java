package com.example.hxl.travel.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.Register;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.RegisterContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.example.hxl.travel.utils.StringUtils;
import com.google.common.base.Preconditions;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class RegisterPresenter extends RxPresenter implements RegisterContract.Presenter{
    RegisterContract.View mView;
    private Subscription subscription;
    private final Context loginContext;

    public RegisterPresenter(RegisterContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        loginContext = mView.getLoginContext();
    }

    @Override
    public void register() {
        //获得用户录入的信息
        final String userPhone = mView.getUserPhone();
        final String userPassword = mView.getUserPassword();
        /*获得共享参数对象*/
        //final SharedPreferences sharedPreferences =
        // SharedPreferencesUtil.getSharedPreferences(loginContext, "user");
        /*获得edit对象*/
        //final SharedPreferences.Editor edit = sharedPreferences.edit();
       if (TextUtils.isEmpty(userPhone)){
            mView.showRegisterMassage(loginContext.getString(R.string.phoneEmpty));
        } else if (!userPhone.matches("[1][3578]\\d{9}")){
            mView.showRegisterMassage(loginContext.getString(R.string.phoneError));
        }else if (TextUtils.isEmpty(userPassword)){
            mView.showRegisterMassage(loginContext.getString(R.string.passwordEmpty));
        }  else if (!userPassword.matches("^[A-Za-z0-9]+$")){
            mView.showRegisterMassage(loginContext.getString(R.string.passwordIsNumberOrLetter));
        } else if (StringUtils.length(userPassword) <= 5){
            mView.showRegisterMassage(loginContext.getString(R.string.passwordLengthError));
        } else {
           subscription = RetrofitHelper.registerObservable(userPhone, userPassword)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Subscriber<Register>() {
                       @Override
                       public void onCompleted() {
                           Log.e("register", "onCompleted: "+"completed");
                       }

                       @Override
                       public void onError(Throwable e) {
                           Log.e("register", "onError: "+e);
                       }

                       @Override
                       public void onNext(Register register) {
                           String type = register.getType();
                           String sessionId = register.getSessionId();
                           String nickname = register.getNickname();

                           if (type.equals("1")){
                               /*存储用户数据*/
                               //edit.putString("userPhone",userPhone);
                               //edit.putString("userPwd",userPassword);
                               //edit.putString("nickname",nickname);
                               //edit.putString("sessionId",sessionId);
                               //提交
                               //edit.commit();
                               mView.showRegisterMassage(loginContext.getString(R.string.registerSuccess));
                               mView.backPage();
                           }else if (type.equals("2")){
                               mView.showRegisterMassage(loginContext.getString(R.string.registerAlready));
                           }else if (type.equals("3")) {

                           }
                       }
                   });
            addSubscribe(subscription);
        }

    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
