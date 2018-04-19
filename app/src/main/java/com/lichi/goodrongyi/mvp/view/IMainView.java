package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NoticeBean;
import com.lichi.goodrongyi.mvp.model.VersionsBean;

/**
 * Created by 默小小 on 2017/11/30.
 */

public interface IMainView extends WrapView {


    void dataVersionSucceed(VersionsBean versionsBean); //版本信息成功

    void dataNoticeSucceed(NoticeBean noticeBean); //公告

    void dataDefeated(String msg); //返回失败

}
