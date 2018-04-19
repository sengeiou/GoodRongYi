package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.UserCardBean;
import com.lichi.goodrongyi.mvp.model.UserCardNumBean;
import com.lichi.goodrongyi.mvp.model.UserCardService;
import com.lichi.goodrongyi.mvp.view.SlotCardTaskView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by ArDu on 2017/12/13.
 */

public class SlotCardTaskPresenter extends BasePresenter<SlotCardTaskView> {

    private UserCardService userCardService;
    private Subscription mSubscription;

    public SlotCardTaskPresenter() {
        userCardService = ServiceFactory.getUserCard();
    }

    //已完成
    public void getUserCard(int page, final int status, String id) {
        mSubscription = RXUtil.execute(userCardService.getUserCard(page, 20, status, id), new Observer<ResponseMessage<UserCardBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后在试");
            }

            @Override
            public void onNext(ResponseMessage<UserCardBean> userCardBeanResponseMessage) {
                if (userCardBeanResponseMessage.statusCode == 0) {
                    mView.dataSuccee(userCardBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userCardBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    //未完成
    public void getUserCardUn(int page, final int status, String id) {
        mSubscription = RXUtil.execute(userCardService.getUserCard(page, 20, status, id), new Observer<ResponseMessage<UserCardBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后在试");
            }

            @Override
            public void onNext(ResponseMessage<UserCardBean> userCardBeanResponseMessage) {
                if (userCardBeanResponseMessage.statusCode == 0) {
                    mView.dataSueeccUn(userCardBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userCardBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    //全部
    public void getUserCardAll(int page, String id) {
        mSubscription = RXUtil.execute(userCardService.getUserCardAll(page, 20, id), new Observer<ResponseMessage<UserCardBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后在试");
            }

            @Override
            public void onNext(ResponseMessage<UserCardBean> userCardBeanResponseMessage) {
                if (userCardBeanResponseMessage.statusCode == 0) {
                    mView.dataSucceeAll(userCardBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userCardBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    //刷卡金额
    public void getUserCardUpdate(int id, String money) {
        mSubscription = RXUtil.execute(userCardService.getUserCardUpdate(id, money), new Observer<ResponseMessage<UserCardBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后在试");
            }

            @Override
            public void onNext(ResponseMessage<UserCardBean> userCardBeanResponseMessage) {
                if (userCardBeanResponseMessage.statusCode == 0) {
                    mView.dataUpdataSueeccUn(userCardBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userCardBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    public void getUserCardTaskCount(String id) {
        mSubscription = RXUtil.execute(userCardService.getUserCardTaskCount(id), new Observer<ResponseMessage<UserCardNumBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后在试");
            }

            @Override
            public void onNext(ResponseMessage<UserCardNumBean> userCardBeanResponseMessage) {
                if (userCardBeanResponseMessage.statusCode == 0) {
                    mView.dataNumBeanSueeccUn(userCardBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(userCardBeanResponseMessage.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

}
