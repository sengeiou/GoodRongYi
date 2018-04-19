package com.lichi.goodrongyi.bean;

import java.io.Serializable;

/**
 * Created by ArDu on 2017/12/7.
 */

public class RankBean implements Serializable {
    private int rank;
    private String name;
    private String Score;
    private String imgUrl;    // 头像地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
