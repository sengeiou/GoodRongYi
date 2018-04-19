package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.LoansBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface LoansView extends WrapView {

    void dataBillSucceed(List<LoansBean> loansBean); //网贷返回成功

    void datagetBannerSucceed(List<LoansBean> loansBean); //网贷返回成功

    void dataDefeated(String msg); //返回失败
}
