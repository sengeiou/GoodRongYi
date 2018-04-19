package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by 默小小 on 2017/12/8.
 */

public class CircleBean implements Serializable {
    private  String imgUrl;
    private String title;
    private  String mainTie;
    private String reTie;


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

    public String getMainTie() {
        return mainTie;
    }

    public void setMainTie(String mainTie) {
        this.mainTie = mainTie;
    }

    public String getReTie() {
        return reTie;
    }

    public void setReTie(String reTie) {
        this.reTie = reTie;
    }
}
