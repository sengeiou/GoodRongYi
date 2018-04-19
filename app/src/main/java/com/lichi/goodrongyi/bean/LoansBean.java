package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by 默小小 on 2017/12/4.
 */

public class LoansBean implements Serializable {
    private String loansImgUrl;
    private String loansDes;
    private String loansActivity;

    public String getLoansImgUrl() {
        return loansImgUrl;
    }

    public void setLoansImgUrl(String loansImgUrl) {
        this.loansImgUrl = loansImgUrl;
    }

    public String getLoansDes() {
        return loansDes;
    }

    public void setLoansDes(String loansDes) {
        this.loansDes = loansDes;
    }

    public String getLoansActivity() {
        return loansActivity;
    }

    public void setLoansActivity(String loansActivity) {
        this.loansActivity = loansActivity;
    }
}
