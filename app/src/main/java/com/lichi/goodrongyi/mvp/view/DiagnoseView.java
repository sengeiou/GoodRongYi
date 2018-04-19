package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.DiagnseListBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.ResultDiagnoseBean;

import java.util.List;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface DiagnoseView extends WrapView {

    void RadarListSucceed(List<String> stringList); //诊断列表

    void CustomerSucceed(CustomerBean bean);

    void dataResultDiagnoseBean(List<ResultDiagnoseBean> diagnoseBean); //新诊断返回成功

    void dataDefeated(String msg); //返回失败

}
