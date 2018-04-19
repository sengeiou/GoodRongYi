package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.QrcodeBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;

/**
 * Created by ArDu on 2017/12/12.
 */

public interface QrcodeView extends WrapView {

    void dataCodeSucceed(QrcodeBean qrcodeBean); //返回成功

    void dataDefeated(String msg); //返回失败
}
