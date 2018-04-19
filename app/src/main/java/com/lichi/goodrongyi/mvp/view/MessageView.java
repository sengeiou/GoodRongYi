package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface MessageView extends WrapView {
    void dataMessageListSucceed(MessageListBean messageListBean); //课程返回成功

    void dataDefeated(String msg); //返回失败
}
