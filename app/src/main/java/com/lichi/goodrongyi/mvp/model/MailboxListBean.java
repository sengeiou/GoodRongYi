package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailboxListBean {
    public String id;

    public String name;

    public String headerUrl;

    public String suffix;

    public int type;
}
