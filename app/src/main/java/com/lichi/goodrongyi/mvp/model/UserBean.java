package com.lichi.goodrongyi.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean implements Serializable, Parcelable {
    public String token;

    public String id;

    public String headImg;

    public String mobile;

    public int vipLevel;

    public String money;

    public String nickname;

    public String rank;

    public String points;

    public String updateTime;

    public String createTime;

    public boolean enabled;

    public String authorities;

    public String username;

    public boolean accountNonLocked;

    public boolean credentialsNonExpired;

    public boolean accountNonExpired;

    public String levelName;

    public String realName;

    public String idCard;

    public UserBean() {

    }

    protected UserBean(Parcel in) {
        token = in.readString();
        id = in.readString();
        headImg = in.readString();
        mobile = in.readString();
        vipLevel = in.readInt();
        money = in.readString();
        nickname = in.readString();
        rank = in.readString();
        points = in.readString();
        updateTime = in.readString();
        createTime = in.readString();
        enabled = in.readByte() != 0;
        authorities = in.readString();
        username = in.readString();
        accountNonLocked = in.readByte() != 0;
        credentialsNonExpired = in.readByte() != 0;
        accountNonExpired = in.readByte() != 0;
        levelName = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(id);
        dest.writeString(headImg);
        dest.writeString(mobile);
        dest.writeInt(vipLevel);
        dest.writeString(money);
        dest.writeString(nickname);
        dest.writeString(rank);
        dest.writeString(points);
        dest.writeString(updateTime);
        dest.writeString(createTime);
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeString(authorities);
        dest.writeString(username);
        dest.writeByte((byte) (accountNonLocked ? 1 : 0));
        dest.writeByte((byte) (credentialsNonExpired ? 1 : 0));
        dest.writeByte((byte) (accountNonExpired ? 1 : 0));
        dest.writeString(levelName);
    }
}
