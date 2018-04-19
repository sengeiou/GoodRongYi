package com.lichi.goodrongyi.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2017/5/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankBean implements Parcelable {

    public String id;
    public String bankname;

    protected BankBean(Parcel in) {
        id = in.readString();
        bankname = in.readString();
    }

    public BankBean() {

    }

    public static final Creator<BankBean> CREATOR = new Creator<BankBean>() {
        @Override
        public BankBean createFromParcel(Parcel in) {
            return new BankBean(in);
        }

        @Override
        public BankBean[] newArray(int size) {
            return new BankBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(bankname);
    }
}
