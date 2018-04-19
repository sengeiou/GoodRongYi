package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.AttestationBean;
import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.ShuaBean;
import com.lichi.goodrongyi.mvp.view.AuthorizeBrandView;
import com.lichi.goodrongyi.mvp.view.AutonymAttestationView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import rx.Observer;
import rx.Subscription;

/**
 * Created by ArDu on 2017/12/13.
 */

public class AutonymAttestationPresenter extends BasePresenter<AutonymAttestationView> {
    private MyCenterService service;
    private Subscription mSubscription;

    public AutonymAttestationPresenter() {
        service = ServiceFactory.getMyCenterService();

    }

    public void getUserReal(String realName, String idCard) {
        mSubscription = RXUtil.execute(service.getUserReal(realName, idCard), new Observer<ResponseMessage<AttestationBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.dataDefeated(e.getMessage());
                mView.setLoadingIndicator(false);

            }

            @Override
            public void onNext(ResponseMessage<AttestationBean> shuaKaBean) {
                if (shuaKaBean.statusCode == 0) {
                    mView.dataUserRealSucceed(shuaKaBean.data);
                } else {
                    mView.dataDefeated(shuaKaBean.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

}
