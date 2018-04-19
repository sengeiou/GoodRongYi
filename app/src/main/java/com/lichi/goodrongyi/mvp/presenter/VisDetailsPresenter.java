package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.BillDeleteBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CreditCardDetailsBean;
import com.lichi.goodrongyi.mvp.model.CreditCardMonthBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VisaPresenterService;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.mvp.view.VisaDetailView;
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

public class VisDetailsPresenter extends BasePresenter<VisaDetailView> {
    private VisaPresenterService mService;
    private Subscription mSubscription;

    public VisDetailsPresenter() {
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

    public void getIndexBillList1(int page, int size, String userId) {
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
                    mView.dataBillSucceed1(verificationCode.data);
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

    public void getIndexBillDetail(String billId, final int groupPosition) {
        mSubscription = RXUtil.execute(mService.getIndexBillDetail(billId), new Observer<ResponseMessage<CreditCardDetailsBean>>() {
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
            public void onNext(ResponseMessage<CreditCardDetailsBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBillDetailsSucceed(verificationCode.data, groupPosition);
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

    public void getBill(String creditId) {
        mSubscription = RXUtil.execute(mService.getBill(creditId, 1, 10000), new Observer<ResponseMessage<CreditCardMonthBean>>() {
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
            public void onNext(ResponseMessage<CreditCardMonthBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBillMonthSucceed(verificationCode.data);
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


    public void getDelete(String billId, String userId) {
        mSubscription = RXUtil.execute(mService.getDelete(billId, userId), new Observer<ResponseMessage<BillDeleteBean>>() {
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
            public void onNext(ResponseMessage<BillDeleteBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBillDeleteSucceed(verificationCode.data);
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

    public void getCreditDelete( String id) {
        mSubscription = RXUtil.execute(mService.getCreditDelete(id), new Observer<ResponseMessage<String>>() {
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
                    mView.datagetCreditDeleteSucceed(verificationCode.data);
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
