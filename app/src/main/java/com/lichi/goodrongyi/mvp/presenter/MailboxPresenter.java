package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.BillService;
import com.lichi.goodrongyi.mvp.model.MailboxListBean;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.MailAddBillView;
import com.lichi.goodrongyi.mvp.view.MailboxView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class MailboxPresenter extends BasePresenter<MailboxView> {
    private BillService mService;
    private Subscription mSubscription;
    public MailboxPresenter() {
        mService = ServiceFactory.getBillService();
    }

    public void getEmailList() {
        mSubscription= RXUtil.execute(mService.getEmailList(), new Observer<ResponseMessage<List<MailboxListBean>>>() {
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
            public void onNext(ResponseMessage<List<MailboxListBean>> verificationCode) {
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

}
