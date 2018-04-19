package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.UserBean;

import java.util.List;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface IIndexView extends WrapView {
    void dataSucceed(UserBean userBean); //头部返回成功

    void dataBillSucceed(List<IndexCreditCardBean> indexCreditCardBean); //账单返回成功

    void dataBillDefeated(); //账单返回失败

    void dataSchoolBeginsSucceed(NewestCourseBean courseBeans); //最新课程返回成功

    void dataVodeoSucceed(NewestVideoBean newestVideoBean); //最新视频返回成功


    void dataMessageSucceed(MessageListBean messageListBean); //信息成功

    void dataMessageDefeated(); //信息返回失败

    void dataDefeated(String msg); //返回失败

}
