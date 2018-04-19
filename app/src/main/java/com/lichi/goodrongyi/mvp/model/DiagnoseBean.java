package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnoseBean implements Serializable {

    public List<Fixed> fixed;

    public List<Percent> percent;

    public String result;

    public String money;

    public String url;

    public String score;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fixed implements Serializable {
        public int id;

        public String userId;

        public int type;

        public String bankname;

        public String name;

        public double consumeMoney;

        public double money;

        public String last4;

        public String holderName;

        public int status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Percent implements Serializable {
        public int id;

        public String userId;

        public int type;

        public String bankname;

        public String name;

        public double consumeMoney;

        public double money;

        public String last4;

        public String holderName;

        public int status;
    }
}
