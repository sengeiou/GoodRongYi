package com.lichi.goodrongyi.mvp.model;

import java.util.Date;

/**
 * Created by 默小小 on 2017/12/22.
 * 刷卡任务
 */

public class ShuaKaRenWuBean {
    private String bankname;
    private int consumeMoney;
    private String holderName;
    private int id;
    private String last4;
    private int money;
    private String name;
    private int status;
    private Date time;
    private int type;
    private String userId;
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    public String getBankname() {
        return bankname;
    }

    public void setConsumeMoney(int consumeMoney) {
        this.consumeMoney = consumeMoney;
    }
    public int getConsumeMoney() {
        return consumeMoney;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    public String getHolderName() {
        return holderName;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }
    public String getLast4() {
        return last4;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public Date getTime() {
        return time;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

}
