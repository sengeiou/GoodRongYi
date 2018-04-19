package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoansBean {
    public int id;

    public String title;

    public String img;

    public String url;

    public String description;
}
