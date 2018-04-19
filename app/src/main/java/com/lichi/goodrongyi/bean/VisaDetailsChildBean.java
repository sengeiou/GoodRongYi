package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by 默小小 on 2017/12/6.
 * 子Item
 */

public class VisaDetailsChildBean implements Serializable {

    private String imgUrl;
    private String title;
    private String date;
    private String price;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
