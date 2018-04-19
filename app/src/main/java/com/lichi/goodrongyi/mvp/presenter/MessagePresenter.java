package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.MessageService;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.VideoService;
import com.lichi.goodrongyi.mvp.view.MessageView;
import com.lichi.goodrongyi.mvp.view.VideoView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public class MessagePresenter extends BasePresenter<MessageView> {

    private MessageService mService;
    private Subscription mSubscription;

    public MessagePresenter() {
        mService = ServiceFactory.getMessageService();
    }

    public void getMessageList(String userId,int page,int size) {
        mSubscription= RXUtil.execute(mService.getMessageList(userId,page,size), new Observer<ResponseMessage<MessageListBean>>() {
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
            public void onNext(ResponseMessage<MessageListBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataMessageListSucceed(verificationCode.data);
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
