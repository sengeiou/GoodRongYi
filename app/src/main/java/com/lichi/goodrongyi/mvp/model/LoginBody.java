package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by test on 2017/12/7.
 */

public class LoginBody {
    @JsonProperty("Content-type")
    public String ContentType="application/x-www-form-urlencoded";

    public String Authorization="Basic bGljaGk6YWJjZGVm";

    public String mobile="";

    public String smsCode="";

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
