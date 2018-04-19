package com.lichi.goodrongyi.mvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by test on 2017/12/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDiagnoseBean implements Serializable {
    public String result = "";
    public String money = "";
    public String url = "";
    public String score = "";
    public String issueBank = "";
    public String holderName = "";
    public String last4digit = "";
    public int progress = 0; //进度多少
    public boolean isAchieve = false; //是否完成进度
    public boolean isBeing = false; //是否正在诊断
    public boolean isUnfold = false; //是否展开
}
