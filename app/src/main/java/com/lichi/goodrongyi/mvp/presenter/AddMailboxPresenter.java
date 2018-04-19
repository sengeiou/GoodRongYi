package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.BillService;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.view.AddMailboxView;
import com.lichi.goodrongyi.mvp.view.MailAddBillView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class AddMailboxPresenter extends BasePresenter<AddMailboxView> {
    private BillService mService;
    private Subscription mSubscription;
    public AddMailboxPresenter() {
        mService = ServiceFactory.getBillService();
    }

    public void getBillData(String userId,String username,String password) {
        mSubscription=RXUtil.execute(mService.getBillData(userId,username,password), new Observer<ResponseMessage<AddMaillboxBean>>() {
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
            public void onNext(ResponseMessage<AddMaillboxBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataSucceed(verificationCode.data);
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
