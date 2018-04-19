package com.lichi.goodrongyi.mvp.presenter;

import android.util.Log;

import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NoticeBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VersionsBean;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class MainPresenter extends BasePresenter<IMainView> {
    private MainService mService;
    private Subscription mSubscription;

    public MainPresenter() {
        mService = ServiceFactory.getMainService();
    }


    public void getNewVersion() {
        Log.d("Version", mService.getNewVersion().toString() + "....");
        mSubscription = RXUtil.execute(mService.getNewVersion(), new Observer<ResponseMessage<VersionsBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.dataDefeated("网络异常，稍候再试！");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onNext(ResponseMessage<VersionsBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataVersionSucceed(verificationCode.data);
                } else {
                    mView.dataDefeated(verificationCode.statusMessage);
                }

            }
        }, new Action0() {
            @Override
            public void call() {
                mView.setLoadingIndicator(true);
            }
        });
        mSubscriptions.add(mSubscription);
    }

    public void getNotice(String  userId) {
        mSubscription = RXUtil.execute(mService.getNotice(userId), new Observer<ResponseMessage<NoticeBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.dataDefeated("网络异常，稍候再试！");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onNext(ResponseMessage<NoticeBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataNoticeSucceed(verificationCode.data);
                } else {
                    mView.dataDefeated(verificationCode.statusMessage);
                }

            }
        }, new Action0() {
            @Override
            public void call() {
                mView.setLoadingIndicator(true);
            }
        });
        mSubscriptions.add(mSubscription);
    }
}
