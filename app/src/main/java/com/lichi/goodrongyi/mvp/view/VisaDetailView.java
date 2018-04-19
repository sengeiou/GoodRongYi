package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.BillDeleteBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CreditCardDetailsBean;
import com.lichi.goodrongyi.mvp.model.CreditCardMonthBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;

import java.util.List;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface VisaDetailView extends WrapView {

    void dataBillSucceed(List<CreditCardBean> indexCreditCardBean); //账单返回成功

    void dataBillSucceed1(List<CreditCardBean> indexCreditCardBean); //修改账单返回成功

    void dataBillDetailsSucceed(CreditCardDetailsBean creditCardDetailsBean, int groupPosition); //账单详情返回成功

    void dataBillMonthSucceed(CreditCardMonthBean creditCardMonthBean); //账单月返回成功

    void dataBillDeleteSucceed(BillDeleteBean billDeleteBean); //删除账单返回成功

    void datagetCreditDeleteSucceed(String billDeleteBean); //删除信用卡返回成功

    void dataDefeated(String msg); //返回失败
}
