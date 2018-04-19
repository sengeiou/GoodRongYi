package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CourseBean;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface CourseView extends WrapView {
    void dataListSucceed(CourseBean courseBeans); //课程返回成功

    void dataDefeated(String msg); //返回失败
}
