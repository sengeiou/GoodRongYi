package com.lichi.goodrongyi.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseBean {
    public int endRow;

    public int firstPage;

    public boolean hasNextPage;

    public boolean hasPreviousPage;

    public boolean isFirstPage;

    public boolean isLastPage;

    public int lastPage;

    public List<DataList> list;

    public int navigateFirstPage;

    public int navigateLastPage;

    public int navigatePages;

    public List<Integer> navigatepageNums;

    public int nextPage;

    public int pageNum;

    public int pageSize;

    public int pages;

    public int prePage;

    public int size;

    public int startRow;

    public int total;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataList {
        public int id;

        public String title;

        public int teacherId;

        public String url;

        public int times;

        public String startTime;

        public String endTime;

        public int person;

        public double money;

        public String address;

        public int fine;

        public int locationId;

        public String updateTime;

        public String createTime;

        public String description;

        public String teachername;

        public String introduce;

        public String imgUrl;

        public int status;
        public String courseUrl;
    }

}
