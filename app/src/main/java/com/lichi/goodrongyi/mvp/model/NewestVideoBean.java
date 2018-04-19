package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewestVideoBean {
    public int pageNum;

    public int pageSize;

    public int size;

    public int startRow;

    public int endRow;

    public int total;

    public int pages;

    public List<DataList> list;

    public int prePage;

    public int nextPage;

    public boolean isFirstPage;

    public boolean isLastPage;

    public boolean hasPreviousPage;

    public boolean hasNextPage;

    public int navigatePages;

    public List<Integer> navigatepageNums;

    public int navigateFirstPage;

    public int navigateLastPage;

    public int firstPage;

    public int lastPage;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataList {
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

        public String title;

    }
}
