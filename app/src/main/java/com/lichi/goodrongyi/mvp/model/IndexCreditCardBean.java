package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexCreditCardBean {
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

    public List<DiagnoseBean.Fixed> fixed = new ArrayList<>();

    public List<DiagnoseBean.Percent> percent = new ArrayList<>();

    public List<String> result = new ArrayList<>();

    public String AddLimit;

}
