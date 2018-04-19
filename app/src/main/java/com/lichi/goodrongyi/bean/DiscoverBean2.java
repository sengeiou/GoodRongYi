package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by Mic-roo on 2017/12/2.
 * Describe：
 */

public class DiscoverBean2 implements Serializable {
    private  String title;
    private String imgURL;
    private String socialType;

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getSedTime() {
        return sedTime;
    }

    public void setSedTime(String sedTime) {
        this.sedTime = sedTime;
    }

    private String  sedTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
