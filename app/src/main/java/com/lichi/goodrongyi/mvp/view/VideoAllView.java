package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface VideoAllView extends WrapView {

    void dataScreenSucceed(List<SelectbankBean> selectbankBeans); //筛选条件

    void dataWeekCourseSucceed(List<WeekCourseBean> selectbankBeans); //最近课程

    void dataVodeoSucceed(NewestVideoBean newestVideoBean); //最新视频返回成功

    void PayVodeoSucceed(String url); //支付

    void PayZfbVodeoSucceed(String url); //支付宝

    void dataDefeated(String msg); //返回失败
}
