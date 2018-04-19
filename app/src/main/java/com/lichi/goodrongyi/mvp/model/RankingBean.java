package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by 默小小 on 2017/12/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankingBean {

    public String id;

    public int rownum;

    public int points;

    public int rank;

    public int diff;

    public int invitation;
}
