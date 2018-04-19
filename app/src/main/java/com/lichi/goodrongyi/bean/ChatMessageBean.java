package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by ArDu on 2017/12/13.
 */

public class ChatMessageBean implements Serializable {
    private String imgUrl;         // 聊天的头像地址
    private String message;        // 聊天的消息内容
    private int type;              // 消息的来源类型 : 0 代表我方的消息，1 代表对方的消息

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
