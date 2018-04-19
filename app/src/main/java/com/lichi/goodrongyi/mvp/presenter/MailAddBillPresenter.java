package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.BillService;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.StorJurisdictionBean;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.mvp.view.MailAddBillView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class MailAddBillPresenter extends BasePresenter<MailAddBillView> {
    private BillService mService;
    private Subscription mSubscription;

    public MailAddBillPresenter() {
        mService = ServiceFactory.getBillService();
    }

    public void getBillDataList(String userId) {
        mSubscription = RXUtil.execute(mService.getBillDataList(userId), new Observer<ResponseMessage<List<MaillboxListBean>>>() {
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
            public void onNext(ResponseMessage<List<MaillboxListBean>> verificationCode) {
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
