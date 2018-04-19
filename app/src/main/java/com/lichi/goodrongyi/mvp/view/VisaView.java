package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.UserBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface VisaView extends WrapView {

    void dataBillSucceed(List<CreditCardBean> indexCreditCardBean); //账单返回成功
    void dataBillDefeated( ); //账单返回失败
    void dataDefeated(String msg); //返回失败
}
