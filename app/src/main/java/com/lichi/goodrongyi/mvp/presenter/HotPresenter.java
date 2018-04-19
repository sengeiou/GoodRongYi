package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.DiscoverService;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.DiscoverView;
import com.lichi.goodrongyi.mvp.view.HotView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class HotPresenter extends BasePresenter<DiscoverView> {
    private DiscoverService service;
    private Subscription mSubscription;

    public HotPresenter() {
        service = ServiceFactory.getDiscoverPresenterService();
    }

    public void getCircleList(int page, int size) {

        mSubscription = RXUtil.execute(service.getSplendorCircleList(page, 20), new Observer<ResponseMessage<HotCircleBean>>() {
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
