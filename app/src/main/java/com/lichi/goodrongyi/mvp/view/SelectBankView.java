package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.BankBean;

import java.util.List;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface SelectBankView extends WrapView{
    void dataListSucceed(List<BankBean> bankBean); //返回成功

    void dataDefeated(String msg); //返回失败
}
