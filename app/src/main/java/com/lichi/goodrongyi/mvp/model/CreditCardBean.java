package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardBean implements Serializable {
    public String id = "";

    public String userId = "";

    public String balanceRmb = "";

    public String issueBank = "";

    public String holderName = "";

    public String last4digit = "";

    public String paymentDueDate = "";

    public String statementDate = "";

    public int freeDay;

    public String statementEndDate = "";

    public String statementStartDate = "";

    public String minPaymentRmb = "";

    public String availablePoints = "";

    public String creditAmt = "";

    public String cashLimitAmt = "";

    public int payDay;

    public int billDay;

    public List<DiagnoseBean.Fixed> fixed = new ArrayList<>();

    public List<DiagnoseBean.Percent> percent = new ArrayList<>();

    public String result = "";

    public String email = "";

    public String money = "";

    public String url = "";

    public String score = "";

    public String AddLimit = "-- --";

    public int progress = 0; //进度多少
    public boolean isAchieve = false; //是否完成进度
    public boolean isBeing = false; //是否正在诊断
    public boolean isUnfold = false; //是否展开
}
