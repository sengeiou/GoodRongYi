package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;

/**
 * Created by Wang on 2017/12/8.
 */

public interface CourseContentView extends WrapView {
    void dataListSucceed(CourseDetailsBean courseBeans); //课程返回成功

    void dataDefeated(String msg); //返回失败
}
