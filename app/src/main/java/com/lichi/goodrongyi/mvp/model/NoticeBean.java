package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeBean {

    public String id;

    public String type;

    public String title;

    public String content;

    public String closeDate;

    public String state;

    public String updateTime;

    public String createTime;

}
