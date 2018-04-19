package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by test on 2018/3/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectbankBean {
    public boolean isSelected = false;

    public String name;

    public int id;

    public String code;

    public String type;

    public String remark;
}
