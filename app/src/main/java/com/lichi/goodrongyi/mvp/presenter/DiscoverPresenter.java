package com.lichi.goodrongyi.mvp.presenter;

import android.content.Context;

import com.lichi.goodrongyi.mvp.model.DiscoverService;
import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.DiscoverView;
import com.lichi.goodrongyi.mvp.view.IIndexView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public class DiscoverPresenter extends BasePresenter<DiscoverView> {
    private DiscoverService service;
    private Subscription mSubscription;
    public DiscoverPresenter() {
        service = ServiceFactory.getDiscoverPresenterService();
    }

    public void getSplendorCircle(int page, int size) {

        mSubscription =   RXUtil.execute(service.getSplendorCircleList(page, size), new Observer<ResponseMessage<HotCircleBean>>() {
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
            public void onNext(ResponseMessage<HotCircleBean> newestCourseBeanResponseMessage) {
                if (newestCourseBeanResponseMessage.statusCode == 0) {
                    mView.dataHotCircleSuccess(newestCourseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(newestCourseBeanResponseMessage.statusMessage);
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
    public void getCircleList(int page, int size) {

        mSubscription =   RXUtil.execute(service.getEssayList(page, size), new Observer<ResponseMessage<EssayListBean>>() {
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
            public void onNext(ResponseMessage<EssayListBean> newestCourseBeanResponseMessage) {
                if (newestCourseBeanResponseMessage.statusCode == 0) {
                    mView.dataCircleListSuccess(newestCourseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(newestCourseBeanResponseMessage.statusMessage);
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
