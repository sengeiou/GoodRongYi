package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by 默小小 on 2017/12/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QrcodeBean {

    public String qrCode;

    public String desc;

    public String text;

    public String headImg;

    public String nickname;
}
