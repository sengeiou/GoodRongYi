package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.VideoService;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;
import com.lichi.goodrongyi.mvp.view.VideoView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public class VideoPresenter extends BasePresenter<VideoView> {

    private VideoService mService;
    private Subscription mSubscription;

    public VideoPresenter() {
        mService = ServiceFactory.getVideoService();
    }

    public void getDataCourse(String id) {
        mSubscription = RXUtil.execute(mService.getDataCourse(id), new Observer<ResponseMessage<VideoDetailsBean>>() {
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
            public void onNext(ResponseMessage<VideoDetailsBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataVideoSucceed(verificationCode.data);
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


    public void getPayCourse(String courseId) {
        mSubscription = RXUtil.execute(mService.getPayCourse("",courseId), new Observer<ResponseMessage<String>>() {
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
            public void onNext(ResponseMessage<String> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.PayVodeoSucceed(verificationCode.data);
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

    public void getZfbPayVideo(String courseId) {
        mSubscription = RXUtil.execute(mService.getZfbPayVideo("",courseId), new Observer<ResponseMessage<String>>() {
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
            public void onNext(ResponseMessage<String> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.PayZfbVodeoSucceed(verificationCode.data);
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
