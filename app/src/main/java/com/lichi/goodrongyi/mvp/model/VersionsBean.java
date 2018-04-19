package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionsBean {
    public String createTime;

    public int id;

    public String information;

    public int status;

    public int type;

    public String updateTime;

    public String url;

    public String version;

    public String versionCode="0";
}
