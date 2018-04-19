package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * Created by 默小小 on 2017/12/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRankBean {

    public int pageNum;

    public int pageSize;

    public int size;

    public int startRow;

    public int endRow;

    public int total;

    public int pages;

    public List<UserBean> list;

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
    public static class UserBean {

        public String id;
        public String rownum;
        public String headImg;
        public String mobile;
        public int vipLevel;
        public int money;
        public String username;
        public String nickname;
        public int rank;
        public int points;
        public String updateTime;
        public String createTime;
        public String pid;
        public int invitation;
        public boolean enabled;
        public boolean accountNonExpired;
        public boolean accountNonLocked;
        public boolean credentialsNonExpired;
    }
}
