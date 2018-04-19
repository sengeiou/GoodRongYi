package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardChildBean {
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
        public String id;

        public String userId;

        public String balanceRmb;

        public String issueBank;

        public String holderName;

        public String last4digit;

        public String paymentDueDate;

        public String statementDate;

        public int freeDay;

        public String statementEndDate;

        public String statementStartDate;

        public String minPaymentRmb;

        public String availablePoints;

        public String creditAmt;

        public String cashLimitAmt;

        public int payDay;

        public int billDay;
    }
}
