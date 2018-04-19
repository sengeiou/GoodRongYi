package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CourseBean;

/**
 * Created by ArDu on 2017/12/11.
 */

public interface PaidClassView extends WrapView {
    void dataDefeated(String msg);
    void dataSueecc(CourseBean bean);
}
