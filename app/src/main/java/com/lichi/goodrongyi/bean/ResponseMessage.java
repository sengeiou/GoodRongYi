package com.lichi.goodrongyi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 *  Author：Jenny
 * Date:2016/12/8 20:13
 * E-mail：fishloveqin@gmail.com
 *  json数据模型抽象
 * 
 */
public class ResponseMessage<T> implements Serializable {
    @JsonProperty("code")
    public int    statusCode;

    @JsonProperty("msg")
    public String statusMessage;

    @JsonProperty("data")
    public T      data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" + "statusCode=" + statusCode + ", statusMessage='" + statusMessage
               + '\'' + ", data=" + data + '}';
    }
}
