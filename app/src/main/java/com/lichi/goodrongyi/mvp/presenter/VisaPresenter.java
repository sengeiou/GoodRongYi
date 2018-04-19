package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.LoginService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VisaPresenterService;
import com.lichi.goodrongyi.mvp.view.IIndexView;
import com.lichi.goodrongyi.mvp.view.VisaView;
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

public class VisaPresenter extends BasePresenter<VisaView> {
    private VisaPresenterService mService;
    private Subscription mSubscription;

    public VisaPresenter() {
        mService = ServiceFactory.getVisaPresenterService();
    }

    public void getIndexBillList(int page, int size, String userId) {
        mSubscription = RXUtil.execute(mService.getIndexBillList(userId, page, size), new Observer<ResponseMessage<List<CreditCardBean>>>() {
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
            public void onNext(ResponseMessage<List<CreditCardBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBillSucceed(verificationCode.data);
                } else if (verificationCode.statusCode == 1) {
                    mView.dataBillDefeated();
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
