package com.lichi.goodrongyi.utill;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import rx.Observer;

/**
 * Created by test on 2018/1/12.
 */

public class MyObserver<T> implements Observer<T> {

    Activity activity;

    public MyObserver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCompleted() {
        if (activity.isFinishing()) {
            return;
        }
    }

    @Override
    public void onError(Throwable e) {
        if (activity.isFinishing()) {
            return;
        }
    }

    @Override
    public void onNext(T t) {
        if (activity.isFinishing()) {
            return;
        }
    }
}
