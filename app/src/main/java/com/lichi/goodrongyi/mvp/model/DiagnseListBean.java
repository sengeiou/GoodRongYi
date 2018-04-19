package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnseListBean {

    public List<Fixed> fixed;

    public List<Percent> percent;

    public List<String> result;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fixed {
        public String bankname;

        public int consumeMoney;

        public String holderName;

        public int id;

        public String last4;

        public int money;

        public String name;

        public int status;

        public String time;

        public int type;

        public String userId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Percent {
        public String bankname;

        public int consumeMoney;

        public String holderName;

        public int id;

        public String last4;

        public int money;

        public String name;

        public int status;

        public String time;

        public int type;

        public String userId;
    }
}
