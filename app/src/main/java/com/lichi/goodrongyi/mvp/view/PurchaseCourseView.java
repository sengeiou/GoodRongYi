package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CoursePayBean;
import com.lichi.goodrongyi.mvp.model.CourseSignUpBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;

/**
 * Created by Wang on 2017/12/11.
 */

public interface PurchaseCourseView extends WrapView {

    void dataListSucceed(CoursePayBean courseBeans); //付费返回成功

    void dataCodeSucceed(VerificationCodeBean verificationCodeBean); //验证码返回成功

    void CustomerSucceed(CustomerBean bean);

    void PayCourseSucceed(String url); //支付
    void PayZfbCourseSucceed(String url); //支付宝

    void dataDefeated(String msg); //返回失败
}
