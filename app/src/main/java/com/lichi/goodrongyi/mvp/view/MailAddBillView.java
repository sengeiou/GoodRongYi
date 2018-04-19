package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;

import java.util.List;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface MailAddBillView extends WrapView{
    void dataListSucceed(List<MaillboxListBean> maillboxListBean); //返回成功

    void dataDefeated(String msg); //返回失败
}
