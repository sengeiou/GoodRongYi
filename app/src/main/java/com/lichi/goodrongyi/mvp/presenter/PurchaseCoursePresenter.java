package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;
import com.lichi.goodrongyi.mvp.model.CoursePayBean;
import com.lichi.goodrongyi.mvp.model.CourseService;
import com.lichi.goodrongyi.mvp.model.CourseSignUpBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.PurchaseCourseView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Wang on 2017/12/11.
 */

public class PurchaseCoursePresenter extends BasePresenter<PurchaseCourseView> {

    private CourseService mService;
    private Subscription mSubscription;

    public PurchaseCoursePresenter() {
        mService = ServiceFactory.getCourseService();
    }


    public void verificationCode(String mobile) {
        mSubscription = RXUtil.execute(mService.getCode(mobile), new Observer<ResponseMessage<VerificationCodeBean>>() {
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
            public void onNext(ResponseMessage<VerificationCodeBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataCodeSucceed(verificationCode.data);
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

    public void getPay(String id, String userId, String money) {
        mSubscription = RXUtil.execute(mService.getPay(id, userId, money), new Observer<ResponseMessage<CoursePayBean>>() {
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
            public void onNext(ResponseMessage<CoursePayBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataListSucceed(verificationCode.data);
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

    public void getCustomer(String type) {
        mSubscription = RXUtil.execute(mService.getCustomer(type), new Observer<ResponseMessage<CustomerBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍候再试！");
            }

            @Override
            public void onNext(ResponseMessage<CustomerBean> rankingBeanResponseMessage) {
                if (rankingBeanResponseMessage.statusCode == 0) {
                    mView.CustomerSucceed(rankingBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(rankingBeanResponseMessage.statusMessage);
                }

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
                    mView.PayCourseSucceed(verificationCode.data);
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

    public void getPayZfbCourse(String courseId) {
        mSubscription = RXUtil.execute(mService.getPayZfbCourse("",courseId), new Observer<ResponseMessage<String>>() {
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
                    mView.PayZfbCourseSucceed(verificationCode.data);
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
