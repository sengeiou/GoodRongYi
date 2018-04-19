package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDetailsBean {
    public int id;

    public String price;

    public int level;

    public String description;

    public String url;

    public String videoUrl;

    public String createTime;

    public String updateTime;

    public int status;

    public String disCount;
}
