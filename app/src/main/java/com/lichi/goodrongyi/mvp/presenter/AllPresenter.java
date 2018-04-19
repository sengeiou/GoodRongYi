package com.lichi.goodrongyi.mvp.presenter;

import android.app.Activity;

import com.lichi.goodrongyi.mvp.model.DiscoverService;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.AllView;
import com.lichi.goodrongyi.mvp.view.DiscoverView;
import com.lichi.goodrongyi.utill.MyObserver;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by 默小小 on 2017/12/8.
 */

public class AllPresenter extends BasePresenter<DiscoverView> {

    private DiscoverService service;
    private Subscription mSubscription;

    public AllPresenter() {
        service = ServiceFactory.getDiscoverPresenterService();
    }

    public void getSplendorCircle(int page, int size) {

        mSubscription = RXUtil.execute(service.getCircleList(page, size), new Observer<ResponseMessage<HotCircleBean>>() {
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
        });
        mSubscriptions.add(mSubscription);
    }


}
