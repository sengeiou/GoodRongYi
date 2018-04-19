package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface EssayView extends WrapView{

    void dataDefeated(String msg); //返回失败
    void dataEssayListSuccess(EssayListBean bean);
}
