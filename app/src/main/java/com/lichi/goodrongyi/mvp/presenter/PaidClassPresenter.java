package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.MyClassService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.view.PaidClassView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;


/**
 * Created by ArDu on 2017/12/11.
 */

public class PaidClassPresenter extends BasePresenter<PaidClassView> {

    private MyClassService classService;
    private Subscription mSubscription;

    public PaidClassPresenter() {
        classService = ServiceFactory.getMyClass();
    }

    public void getMyClass(int page, int status, String id) {
        mSubscription = RXUtil.execute(classService.getClass(page, 20, status, id), new Observer<ResponseMessage<CourseBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍后再试");

            }

            @Override
            public void onNext(ResponseMessage<CourseBean> courseBeanResponseMessage) {
                if (courseBeanResponseMessage.statusCode == 0) {
                    mView.dataSueecc(courseBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(courseBeanResponseMessage.statusMessage);
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
