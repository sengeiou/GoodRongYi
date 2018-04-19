package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.DiagnseListBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.ResultDiagnoseBean;
import com.lichi.goodrongyi.mvp.model.StorJurisdictionBean;
import com.lichi.goodrongyi.mvp.model.VisaPresenterService;
import com.lichi.goodrongyi.mvp.view.DiagnoseView;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class DiagnosePresenter extends BasePresenter<DiagnoseView> {
    private VisaPresenterService mService;
    private Subscription mSubscription;

    public DiagnosePresenter() {
        mService = ServiceFactory.getVisaPresenterService();
    }


    public void getRadarList() {
        mSubscription = RXUtil.execute(mService.getRadarList(), new Observer<ResponseMessage<List<String>>>() {
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
            public void onNext(ResponseMessage<List<String>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.RadarListSucceed(verificationCode.data);
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

    public void getCustomer(int type) {
        mSubscription = RXUtil.execute(mService.getCustomer(type + ""), new Observer<ResponseMessage<CustomerBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍候再试！");
            }

            @Override
            public void onNext(ResponseMessage<CustomerBean> rankingBeanResponseMessage) {
                if (rankingBeanResponseMessage.statusCode == 0) {
                    mView.CustomerSucceed(rankingBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(rankingBeanResponseMessage.statusMessage);
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



    public void getResultDiagnosis(String userId) {
        mSubscription = RXUtil.execute(mService.getResultDiagnosis(userId), new Observer<ResponseMessage<List<ResultDiagnoseBean>>>() {
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
            public void onNext(ResponseMessage<List<ResultDiagnoseBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataResultDiagnoseBean(verificationCode.data);
                } else {
                    mView.dataDefeated(verificationCode.statusMessage);
                    mView.setLoadingIndicator(false);
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
