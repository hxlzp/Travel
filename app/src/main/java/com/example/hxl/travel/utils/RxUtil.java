package com.example.hxl.travel.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */
public class RxUtil {
    /**
     * 统一线程处理
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 生成Observable
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
