package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardDetailsBean {
    public int pageNum;

    public int pageSize;

    public int size;

    public int startRow;

    public int endRow;

    public int total;

    public int pages;

    public List<DataList> list ;

    public int prePage;

    public int nextPage;

    public boolean isFirstPage;

    public boolean isLastPage;

    public boolean hasPreviousPage;

    public boolean hasNextPage;

    public int navigatePages;

    public List<Integer> navigatepageNums ;

    public int navigateFirstPage;

    public int navigateLastPage;

    public int firstPage;

    public int lastPage;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataList{
        public int id;

        public String creditBillId;

        public String transDate;

        public String statementStartDate;

        public String statementEndDate;

        public String last4digit;

        public String postCurrency;

        public String transDesc;

        public String postAmt;
    }
}
