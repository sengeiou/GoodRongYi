package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by test on 2018/3/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeekCourseBean {
    public int id;

    public String price;

    public int level;

    public String description;

    public String url;

    public String videoUrl;

    public String code;

    public String createTime;

    public String updateTime;

    public int status;

    public String disCount;

    public String title;
}
