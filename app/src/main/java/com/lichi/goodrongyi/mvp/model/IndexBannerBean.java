package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by test on 2018/3/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexBannerBean {
    public String id;
    public String image;
    public String text;
    public String value;
    public String status;
    public String sort;
}
