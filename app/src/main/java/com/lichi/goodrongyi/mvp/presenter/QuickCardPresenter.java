package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.LoansBean;
import com.lichi.goodrongyi.mvp.model.LoansService;
import com.lichi.goodrongyi.mvp.model.QuickCardBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.LoansView;
import com.lichi.goodrongyi.mvp.view.QuickCardView;
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

public class QuickCardPresenter extends BasePresenter<QuickCardView> {

    private LoansService mService;
    private Subscription mSubscription;

    public QuickCardPresenter() {
        mService = ServiceFactory.getLoansService();
    }

    public void getNetloan() {
        mSubscription=  RXUtil.execute(mService.getquickCard(0), new Observer<ResponseMessage<List<QuickCardBean>>>() {
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
            public void onNext(ResponseMessage<List<QuickCardBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBillSucceed(verificationCode.data);
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

    public void getBanner() {
        mSubscription=   RXUtil.execute(mService.getquickCard(1), new Observer<ResponseMessage<List<QuickCardBean>>>() {
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
            public void onNext(ResponseMessage<List<QuickCardBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.datagetBannerSucceed(verificationCode.data);
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
