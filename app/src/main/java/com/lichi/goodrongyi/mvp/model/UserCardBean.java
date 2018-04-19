package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by 默小小 on 2017/12/27.
 * 刷卡任务Bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCardBean {

    public int pageNum;

    public int pageSize;

    public int size;

    public int startRow;

    public int endRow;

    public int total;

    public int pages;
    //TODO
    public List<SlotCardTasksBean> list;

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

    public int lastPage;

    public int firstPage;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SlotCardTasksBean {
        public int id;
        public String userId;
        public int type;
        public String bankname;
        public String name;
        public int consumeMoney;
        public int money;
        public String time;
        public String last4;
        public String holderName;
        public int status;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NavigatepageNums{}

}
