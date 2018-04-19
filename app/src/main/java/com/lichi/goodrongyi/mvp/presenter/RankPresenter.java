package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UserRankBean;
import com.lichi.goodrongyi.mvp.view.RankView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by ArDu on 2017/12/13.
 */

public class RankPresenter extends BasePresenter<RankView> {
    private MyCenterService service;
    private Subscription mSubscription;

    public RankPresenter() {
        service = ServiceFactory.getMyCenterService();
    }

    public void getRank(int page, int size) {
        mSubscription = RXUtil.execute(service.getUserRank(page, size), new Observer<ResponseMessage<UserRankBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，请稍后再试！");

            }

            @Override
            public void onNext(ResponseMessage<UserRankBean> userRankBeanResponseMessage) {
                if (userRankBeanResponseMessage.statusCode == 0) {
                    mView.rankSueecc(userRankBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userRankBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }
}
