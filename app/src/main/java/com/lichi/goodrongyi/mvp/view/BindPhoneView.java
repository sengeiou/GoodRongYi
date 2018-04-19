package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;

/**
 * Created by ArDu on 2017/12/12.
 */

public interface BindPhoneView extends WrapView {

    void dataCodeSucceed(VerificationCodeBean verificationCodeBean); //验证码返回成功

    void changeSucceed(String msg);

    void dataDefeated(String msg); //返回失败
}
