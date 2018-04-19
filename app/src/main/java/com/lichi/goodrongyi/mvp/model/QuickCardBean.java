package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuickCardBean {
    public int id;

    public String title;

    public String img;

    public String url;

    public String description;
}
