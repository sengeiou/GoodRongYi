package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.CourseService;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.StorJurisdictionBean;
import com.lichi.goodrongyi.mvp.view.CourseView;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class CoursePresenter extends BasePresenter<CourseView> {
    private CourseService mService;
    private Subscription mSubscription;

    public CoursePresenter() {
        mService = ServiceFactory.getCourseService();
    }

    public void getDataList(String locationId, String userId, int page) {
        mSubscription = RXUtil.execute(mService.getDataList(locationId, userId, page, 20), new Observer<ResponseMessage<CourseBean>>() {
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
            public void onNext(ResponseMessage<CourseBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataListSucceed(verificationCode.data);
                } else {
                    mView.dataDefeated(verificationCode.statusMessage);
                }

            }
        }, new Action0() {
            @Override
            public void call() {
                // mView.setLoadingIndicator(true);
            }
        });
        mSubscriptions.add(mSubscription);
    }

}
