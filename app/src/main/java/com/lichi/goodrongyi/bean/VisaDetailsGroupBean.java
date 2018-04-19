package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by 默小小 on 2017/12/6.
 * 父Item
 */

public class VisaDetailsGroupBean implements Serializable {
    private String dateOfYean;
    private String startDate;
    private String endDate;
    private  float totalPrice;

    public String getDateOfYean() {
        return dateOfYean;
    }

    public void setDateOfYean(String dateOfYean) {
        this.dateOfYean = dateOfYean;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
