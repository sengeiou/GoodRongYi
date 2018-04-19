package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.VideoService;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;
import com.lichi.goodrongyi.mvp.view.VideoAllView;
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

public class VideoAllPresenter extends BasePresenter<VideoAllView> {

    private VideoService mService;
    private Subscription mSubscription;

    public VideoAllPresenter() {
        mService = ServiceFactory.getVideoService();
    }


    public void getDataDict() {
        mSubscription = RXUtil.execute(mService.getDataDict(), new Observer<ResponseMessage<List<SelectbankBean>>>() {
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
            public void onNext(ResponseMessage<List<SelectbankBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataScreenSucceed(verificationCode.data);
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

    public void getDataLast() {
        mSubscription = RXUtil.execute(mService.getDataLast(), new Observer<ResponseMessage<List<WeekCourseBean>>>() {
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
            public void onNext(ResponseMessage<List<WeekCourseBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataWeekCourseSucceed(verificationCode.data);
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

    public void getIndexVideoList(int page,String code) {
        mSubscription = RXUtil.execute(mService.getVideoCodeList(page, 20,code), new Observer<ResponseMessage<NewestVideoBean>>() {
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
            public void onNext(ResponseMessage<NewestVideoBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataVodeoSucceed(verificationCode.data);
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
