package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;
import com.lichi.goodrongyi.mvp.model.CourseService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.CourseContentView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Wang on 2017/12/8.
 */

public class CourseContentPresenter extends BasePresenter<CourseContentView> {
    private CourseService mService;
    private Subscription mSubscription;

    public CourseContentPresenter() {
        mService = ServiceFactory.getCourseService();
    }


    public void getDataCourse(int id) {
        mSubscription = RXUtil.execute(mService.getDataCourseUser(id), new Observer<ResponseMessage<CourseDetailsBean>>() {
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
            public void onNext(ResponseMessage<CourseDetailsBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataListSucceed(verificationCode.data);
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
