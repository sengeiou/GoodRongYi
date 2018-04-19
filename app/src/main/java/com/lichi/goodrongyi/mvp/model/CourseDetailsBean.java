package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDetailsBean {
    public int id;

    public String title;

    public int teacherId;

    public String url;

    public int times;

    public String startTime;

    public String endTime;

    public int person;

    public String money;

    public String address;

    public int fine;

    public int locationId;

    public String updateTime;

    public String createTime;

    public String description;

    public String teachername;

    public String introduce;

    public String imgUrl;

    public String courseUrl;

    public String disCount;

    public int status;
}
