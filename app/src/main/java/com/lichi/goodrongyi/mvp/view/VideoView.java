package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface VideoView extends WrapView {
    void dataVideoSucceed(VideoDetailsBean courseBeans); //课程返回成功

    void PayVodeoSucceed(String url); //支付

    void PayZfbVodeoSucceed(String url); //支付宝

    void dataDefeated(String msg); //返回失败
}
