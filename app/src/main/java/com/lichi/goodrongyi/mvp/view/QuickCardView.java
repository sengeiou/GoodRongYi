package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.LoansBean;
import com.lichi.goodrongyi.mvp.model.QuickCardBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface QuickCardView extends WrapView {

    void dataBillSucceed(List<QuickCardBean> loansBean); //网贷返回成功

    void datagetBannerSucceed(List<QuickCardBean> loansBean); //Banner网贷返回成功

    void dataDefeated(String msg); //返回失败
}
