package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.MyCourseBean;
import com.lichi.goodrongyi.mvp.model.RankingBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.ShuaBean;
import com.lichi.goodrongyi.mvp.model.UpLoadingFileBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.view.MineView;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public class MinePresenter extends BasePresenter<MineView> {

    private MyCenterService service;
    private Subscription mSubscription;

    public MinePresenter() {
        service = ServiceFactory.getMyCenterService();

    }

    public void getShuaKa(String id) {
        mSubscription = RXUtil.execute(service.getTaskCount(id), new Observer<ResponseMessage<ShuaBean>>() {
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
            public void onNext(ResponseMessage<ShuaBean> shuaKaBean) {
                if (shuaKaBean.statusCode == 0) {
                    mView.ShuaKaSucceed(shuaKaBean.data);
                } else {
                    mView.dataDefeated(shuaKaBean.statusMessage);
                }
            }
        });
        mSubscriptions.add(mSubscription);
    }

    public void getCourse(String id) {
        mSubscription = RXUtil.execute(service.getMyCourse(id), new Observer<ResponseMessage<MyCourseBean>>() {
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
            public void onNext(ResponseMessage<MyCourseBean> shuaKaBean) {
                if (shuaKaBean.statusCode == 0) {
                    mView.MyCourseSucceed(shuaKaBean.data);
                } else {
                    mView.dataDefeated(shuaKaBean.statusMessage);
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

    public void getSignUp() {
        mSubscription = RXUtil.execute(service.getUser(), new Observer<ResponseMessage<UserBean>>() {
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
            public void onNext(ResponseMessage<UserBean> userBean) {
                if (userBean.statusCode == 0) {
                    try {
                        mView.dataUserBeanucceed(userBean.data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mView.dataDefeated(userBean.statusMessage);
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

    public void getPostUser(Map<String, String> map) {
        mSubscription = RXUtil.execute(service.getPostUser(map), new Observer<ResponseMessage<UpLoadingFileBean>>() {
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
            public void onNext(ResponseMessage<UpLoadingFileBean> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataModificationSucceed(userBean.data);
                } else {
                    mView.dataDefeated(userBean.statusMessage);
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

    public void uploadFile(MultipartBody.Part parts) {
        mSubscription = RXUtil.execute(service.uploadFile(parts), new Observer<ResponseMessage<String>>() {
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
            public void onNext(ResponseMessage<String> userBean) {
                if (userBean.statusCode == 0) {
                    mView.dataFileBeanucceed(userBean.data);
                } else {
                    mView.dataDefeated(userBean.statusMessage);
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

    public void getRanking(String id) {
        mSubscription = RXUtil.execute(service.getRanking(id), new Observer<ResponseMessage<RankingBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍候再试！");
            }

            @Override
            public void onNext(ResponseMessage<RankingBean> rankingBeanResponseMessage) {
                if (rankingBeanResponseMessage.statusCode == 0) {
                    mView.rankingSucceed(rankingBeanResponseMessage.data);
                } else {
                    mView.dataDefeated(rankingBeanResponseMessage.statusMessage);
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

    public void getCustomer(final int type) {
        mSubscription = RXUtil.execute(service.getCustomer(type+""), new Observer<ResponseMessage<CustomerBean>>() {
            @Override
            public void onCompleted() {
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.setLoadingIndicator(false);
                mView.dataDefeated("网络异常，稍候再试！");
            }

            @Override
            public void onNext(ResponseMessage<CustomerBean> rankingBeanResponseMessage) {
                if (rankingBeanResponseMessage.statusCode == 0) {
                    mView.CustomerSucceed(rankingBeanResponseMessage.data,type);
                } else {
                    mView.dataDefeated(rankingBeanResponseMessage.statusMessage);
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
