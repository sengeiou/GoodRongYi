package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;
import com.lichi.goodrongyi.mvp.model.CourseSignUpBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;

/**
 * Created by Wang on 2017/12/7.
 */

public interface SignUpCourseView extends WrapView {
    void dataListSucceed(CourseSignUpBean courseBeans); //课程返回成功

    void dataCodeSucceed(VerificationCodeBean verificationCodeBean); //验证码返回成功
    void dataUserBeanucceed(UserBean userBean); //用户信息成功
    void dataDefeated(String msg); //返回失败
}
