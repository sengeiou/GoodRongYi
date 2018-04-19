package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.bean.cheshi;
import com.lichi.goodrongyi.mvp.model.LogInBean;
import com.lichi.goodrongyi.mvp.model.LoginService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.LoginView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginService mService;
    private Subscription mSubscription;
    public LoginPresenter() {
        mService = ServiceFactory.getLoginService();
    }


    public void verificationCode(String mobile) {
        mSubscription= RXUtil.execute(mService.getCode(mobile), new Observer<ResponseMessage<VerificationCodeBean>>() {
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

    public void getLogIn(String mobile, String smsCode) {
        mSubscription= RXUtil.execute(mService.getLogIn(mobile, smsCode), new Observer<ResponseMessage<LogInBean>>() {

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
            public void onNext(ResponseMessage<LogInBean> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataLoginSucceed(userBean.data.token);
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

    public void getsignUp(String token) {
        mSubscription=  RXUtil.execute(mService.getUser(token), new Observer<ResponseMessage<UserBean>>() {
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

    public void getOpenid(String openid, String providerId, String nickname, String headimgurl) {
        mSubscription= RXUtil.execute(mService.getOpenid(openid, providerId, nickname, headimgurl), new Observer<ResponseMessage<LogInBean>>() {
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
            public void onNext(ResponseMessage<LogInBean> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataLoginSucceed(userBean.data.token);
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
