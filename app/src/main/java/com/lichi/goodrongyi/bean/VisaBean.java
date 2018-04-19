package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by 默小小 on 2017/12/4.
 */

public class VisaBean implements Serializable {

    private String bankImgUrl;
    private String bankName;
    private String limit;
    private String name;
    private String nameOfNumber;
    private String tempLimit;
    private String surplus;
    private String billDate;
    private String refundDate;
    public int progress = 0; //进度多少
    public boolean isAchieve = false; //是否完成进度
    public boolean isBeing = false; //是否正在诊断

    public String getBankImgUrl() {
        return bankImgUrl;
    }

    public void setBankImgUrl(String bankImgUrl) {
        this.bankImgUrl = bankImgUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfNumber() {
        return nameOfNumber;
    }

    public void setNameOfNumber(String nameOfNumber) {
        this.nameOfNumber = nameOfNumber;
    }

    public String getTempLimit() {
        return tempLimit;
    }

    public void setTempLimit(String tempLimit) {
        this.tempLimit = tempLimit;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }
}
