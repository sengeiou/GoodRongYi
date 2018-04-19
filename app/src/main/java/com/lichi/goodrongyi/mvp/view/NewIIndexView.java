package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.IndexBannerBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface NewIIndexView extends WrapView {
    void dataSchoolBeginsSucceed(NewestCourseBean courseBeans); //最新课程返回成功

    void dataVodeoSucceed(NewestVideoBean newestVideoBean); //最新视频返回成功

    void dataMessageSucceed(MessageListBean messageListBean); //信息成功

    void dataWeekCourseSucceed(List<WeekCourseBean> selectbankBeans); //最近课程

    void PayZfbVodeoSucceed(String url); //支付宝

    void dataFreeVodeoSucceed(NewestVideoBean newestVideoBean); //免费视频

    void dataBannerSucceed(List<IndexBannerBean> indexBannerBeans); //轮播

    void dataDefeated(String msg); //返回失败

}
