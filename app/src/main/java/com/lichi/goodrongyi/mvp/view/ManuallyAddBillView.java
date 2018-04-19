package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface ManuallyAddBillView extends WrapView {
    void dataListSucceed(AddMaillboxBean maillboxListBean); //返回成功

    void dataDefeated(String msg); //返回失败
}
