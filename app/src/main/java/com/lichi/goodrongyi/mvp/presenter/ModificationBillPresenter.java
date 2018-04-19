package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.BillService;
import com.lichi.goodrongyi.mvp.model.ModificationMaillboxBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.ImportBillView;
import com.lichi.goodrongyi.mvp.view.ModificationBillView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class ModificationBillPresenter extends BasePresenter<ModificationBillView> {
    private BillService mService;
    private Subscription mSubscription;
    public ModificationBillPresenter() {
        mService = ServiceFactory.getBillService();
    }

    public void getBillDataAdd(Map<String, String> params) {
        mSubscription=RXUtil.execute(mService.getCreditEdit(params), new Observer<ResponseMessage<String>>() {
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
