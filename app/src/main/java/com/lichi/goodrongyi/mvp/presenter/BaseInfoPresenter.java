package com.lichi.goodrongyi.mvp.presenter;


import com.lichi.goodrongyi.mvp.model.CreditBaseBean;
import com.lichi.goodrongyi.mvp.model.CreditInvestigationBean;
import com.lichi.goodrongyi.mvp.model.HouseFundBaseBean;
import com.lichi.goodrongyi.mvp.model.QueryService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.SocialBaseBean;
import com.lichi.goodrongyi.mvp.model.StudyBaseBean;
import com.lichi.goodrongyi.mvp.view.BaseInfoView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by 默小小 on 2017/12/13.
 */

public class BaseInfoPresenter extends BasePresenter<BaseInfoView> {
    private QueryService service;
    private Subscription mSubscription;
    public BaseInfoPresenter() {
        service= ServiceFactory.getmQueryService();
    }
    public void getHouseFundBaseInfo(String area,String username,String password,String otherInfo,String realName){
        mSubscription=  RXUtil.execute(service.getHouseFund(area, username, password, otherInfo, realName), new Observer<ResponseMessage<HouseFundBaseBean>>() {

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
            public void onNext(ResponseMessage<HouseFundBaseBean> houseFundBeanResponseMessage) {
                if (houseFundBeanResponseMessage.statusCode == 0) {
                    mView.queryHouseFundSuccess(houseFundBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(houseFundBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }
    public void getCreditInvestigationBaseInfo(String middleAuthCode,String password,String username){
        mSubscription=   RXUtil.execute(service.getCreditInvestigation(middleAuthCode, username, password), new Observer<ResponseMessage<CreditInvestigationBean>>() {

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
            public void onNext(ResponseMessage<CreditInvestigationBean> creditBaseBeanResponseMessage) {
                if (creditBaseBeanResponseMessage.statusCode == 0) {
                    mView.queryCreditSuccess(creditBaseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(creditBaseBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }
    public void getSocialsecurityBaseInfo( String area,  String password,  String username,  String otherInfo, String realName){
        mSubscription=     RXUtil.execute(service.getSocialsecurity(area, username, password, otherInfo, realName), new Observer<ResponseMessage<SocialBaseBean>>() {
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
            public void onNext(ResponseMessage<SocialBaseBean> houseFundBeanResponseMessage) {
                if (houseFundBeanResponseMessage.statusCode == 0) {
                    mView.querySocialSecuritySuccess(houseFundBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(houseFundBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }
    public void getStudyBaseInfo( String username,String password, String method){
        mSubscription=     RXUtil.execute(service.getStudy(method, username, password), new Observer<ResponseMessage<StudyBaseBean>>() {
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
            public void onNext(ResponseMessage<StudyBaseBean> houseFundBeanResponseMessage) {
                if (houseFundBeanResponseMessage.statusCode == 0) {
                    mView.queryStudySuccess(houseFundBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(houseFundBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }
}
