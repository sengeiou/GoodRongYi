package com.lichi.goodrongyi.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;


/**
 * Created by WEK on 2016/10/25.
 */

public abstract class BasePresenter<T> {
    public Context context;

    public T mView;
    public List<Subscription> mSubscriptions = new ArrayList<Subscription>();

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
       // mView = null;
        int size = mSubscriptions.size();
        for (int i = 0; i < size; i++) {

            Subscription subscription = mSubscriptions.get(i);
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
        mSubscriptions.clear();
    }




}
