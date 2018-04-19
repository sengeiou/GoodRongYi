package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface LoginView extends WrapView {

    void dataCodeSucceed(VerificationCodeBean verificationCodeBean); //验证码返回成功

    void dataLoginSucceed(String userBean); //登录返回成功

    void dataUserBeanucceed(UserBean userBean); //用户信息成功

    void dataDefeated(String msg); //返回失败

}
