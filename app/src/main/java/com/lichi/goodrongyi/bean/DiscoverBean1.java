package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by Mic-roo on 2017/12/2.
 * Describe：
 */

public class DiscoverBean1 implements Serializable {
    private  String title;
    private String imgURL;

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
