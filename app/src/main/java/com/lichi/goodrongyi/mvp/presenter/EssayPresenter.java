package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.DiscoverService;
import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.EssayView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by momoc on 2017/12/13.
 */

public class EssayPresenter extends BasePresenter<EssayView> {
    private DiscoverService service;
    private Subscription mSubscription;

    public EssayPresenter() {
        service = ServiceFactory.getDiscoverPresenterService();
    }

    public void getSplendorCircle(String circleId, int page) {

        mSubscription = RXUtil.execute(service.getArticleList(circleId, page, 20), new Observer<ResponseMessage<EssayListBean>>() {
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
                    mView.dataEssayListSuccess(newestCourseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(newestCourseBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    public void getArticleHotList(int page) {

        mSubscription = RXUtil.execute(service.getArticleHotList(page, 20), new Observer<ResponseMessage<EssayListBean>>() {
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
                    mView.dataEssayListSuccess(newestCourseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(newestCourseBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }


}
