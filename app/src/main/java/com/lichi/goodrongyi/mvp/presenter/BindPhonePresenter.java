package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.LoginService;
import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.MyClassService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UpLoadingFileBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.BindPhoneView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by ArDu on 2017/12/12.
 */

public class BindPhonePresenter extends BasePresenter<BindPhoneView> {
    private LoginService mService;
    private MyCenterService myClassService;
    private Subscription mSubscription;

    public BindPhonePresenter() {
        myClassService = ServiceFactory.getMyCenterService();
        mService = ServiceFactory.getLoginService();
    }


    public void verificationCode(String mobile) {
        mSubscription=RXUtil.execute(mService.getCode(mobile), new Observer<ResponseMessage<VerificationCodeBean>>() {
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

    public void commitInfo(Map<String, String> map) {
        mSubscription= RXUtil.execute(myClassService.getPostUser(map), new Observer<ResponseMessage<UpLoadingFileBean>>() {

            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后再试");
            }

            @Override
            public void onNext(ResponseMessage<UpLoadingFileBean> verificationCodeBeanResponseMessage) {
                if (verificationCodeBeanResponseMessage.statusCode == 0) {
                    mView.changeSucceed("修改成功！");
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
