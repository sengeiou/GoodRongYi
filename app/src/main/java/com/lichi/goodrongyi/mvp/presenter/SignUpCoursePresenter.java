package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;
import com.lichi.goodrongyi.mvp.model.CourseService;
import com.lichi.goodrongyi.mvp.model.CourseSignUpBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.SignUpCourseView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Wang on 2017/12/7.
 */

public class SignUpCoursePresenter extends BasePresenter<SignUpCourseView> {
    private CourseService mService;
    private Subscription mSubscription;

    public SignUpCoursePresenter() {
        mService = ServiceFactory.getCourseService();
    }


    public void getSignUp(Map<String, String> params) {
        mSubscription = RXUtil.execute(mService.getSignUp(params), new Observer<ResponseMessage<CourseSignUpBean>>() {
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
            public void onNext(ResponseMessage<CourseSignUpBean> verificationCode) {
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

    public void getUser(String token) {
        mSubscription = RXUtil.execute(mService.getUser(token), new Observer<ResponseMessage<UserBean>>() {
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
            public void onNext(ResponseMessage<UserBean> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataUserBeanucceed(userBean.data);
                } else {
                    mView.dataDefeated(userBean.statusMessage);
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

    public void getUser() {
        mSubscription = RXUtil.execute(mService.getUser(), new Observer<ResponseMessage<UserBean>>() {
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
            public void onNext(ResponseMessage<UserBean> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataUserBeanucceed(userBean.data);
                } else {
                    mView.dataDefeated(userBean.statusMessage);
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
