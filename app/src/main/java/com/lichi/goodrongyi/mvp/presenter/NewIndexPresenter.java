package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.IndexBannerBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.ResponseMessage;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;
import com.lichi.goodrongyi.mvp.view.IIndexView;
import com.lichi.goodrongyi.mvp.view.NewIIndexView;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.RXUtil;
import com.lichi.goodrongyi.utill.ServiceFactory;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public class NewIndexPresenter extends BasePresenter<NewIIndexView> {

    private MainService mService;
    private Subscription mSubscription;

    public NewIndexPresenter() {
        mService = ServiceFactory.getMainService();
    }


    public void getDataLast() {
        mSubscription = RXUtil.execute(mService.getDataLast(), new Observer<ResponseMessage<List<WeekCourseBean>>>() {
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
            public void onNext(ResponseMessage<List<WeekCourseBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataWeekCourseSucceed(verificationCode.data);
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

    public void getIndexCourseList(int page) {
        mSubscription = RXUtil.execute(mService.getIndexCourseList(IOUtils.getUserId(mView.getContext()), page, 3), new Observer<ResponseMessage<NewestCourseBean>>() {
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
            public void onNext(ResponseMessage<NewestCourseBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataSchoolBeginsSucceed(verificationCode.data);
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

    public void getIndexVideoList(int page) {
        mSubscription = RXUtil.execute(mService.getIndexVideoList(page, 8), new Observer<ResponseMessage<NewestVideoBean>>() {
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
            public void onNext(ResponseMessage<NewestVideoBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataVodeoSucceed(verificationCode.data);
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

    public void getMessageList(String userId) {
        mSubscription = RXUtil.execute(mService.getMessageList(userId, 1, 3), new Observer<ResponseMessage<MessageListBean>>() {
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
                    mView.dataMessageSucceed(verificationCode.data);
                } else if (verificationCode.statusCode == 1) {
                    // mView.dataMessageDefeated();
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


    public void getZfbPayVideo(String courseId) {
        mSubscription = RXUtil.execute(mService.getZfbPayVideo("", courseId), new Observer<ResponseMessage<String>>() {
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
            public void onNext(ResponseMessage<String> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.PayZfbVodeoSucceed(verificationCode.data);
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


    public void getVideoFree(int page) {
        mSubscription = RXUtil.execute(mService.getVideoFree(page, 8), new Observer<ResponseMessage<NewestVideoBean>>() {
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
            public void onNext(ResponseMessage<NewestVideoBean> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataFreeVodeoSucceed(verificationCode.data);
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

    public void getBanner() {
        mSubscription = RXUtil.execute(mService.getBanner(), new Observer<ResponseMessage<List<IndexBannerBean>>>() {
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
            public void onNext(ResponseMessage<List<IndexBannerBean>> verificationCode) {
                if (verificationCode.statusCode == 0) {
                    mView.dataBannerSucceed(verificationCode.data);
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
