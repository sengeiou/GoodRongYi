package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.LoginService;
import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.QrcodeBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UpLoadingFileBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.BindPhoneView;
import com.lichi.goodrongyi.mvp.view.QrcodeView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by ArDu on 2017/12/12.
 */

public class QrcodePresenter extends BasePresenter<QrcodeView> {
    private MyCenterService mService;
    private Subscription mSubscription;

    public QrcodePresenter() {
        mService = ServiceFactory.getMyCenterService();

    }

    public void getqrCode() {
        mSubscription=RXUtil.execute(mService.getqrCode(), new Observer<ResponseMessage<QrcodeBean>>() {
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
            public void onNext(ResponseMessage<QrcodeBean> verificationCode) {
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


}
