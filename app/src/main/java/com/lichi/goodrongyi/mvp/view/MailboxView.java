package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.MailboxListBean;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;

import java.util.List;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface MailboxView extends WrapView{
    void dataListSucceed(List<MailboxListBean> maillboxListBean); //返回成功

    void dataDefeated(String msg); //返回失败
}
