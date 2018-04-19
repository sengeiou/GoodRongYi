package com.lichi.goodrongyi.utill;

import android.widget.ImageView;

import com.lichi.goodrongyi.R;

/**
 * Created by test on 2018/1/9.
 */

public class BankIconUtils {

    public static void setBankIcon(ImageView itemBankIcon, String BankName) {
        switch (BankName) {
            case "中国工商银行":
                itemBankIcon.setImageResource(R.drawable.gsyh);
                break;
            case "中国建设银行":
                itemBankIcon.setImageResource(R.drawable.jsyh);
                break;
            case "中国光大银行":
                itemBankIcon.setImageResource(R.drawable.gdyh);
                break;
            case "中国农业银行":
                itemBankIcon.setImageResource(R.drawable.nyyh);
                break;
            case "交通银行":
                itemBankIcon.setImageResource(R.drawable.jtyh);
                break;
            case "招商银行":
                itemBankIcon.setImageResource(R.drawable.zsyh);
                break;
            case "中国民生银行":
                itemBankIcon.setImageResource(R.drawable.msyh);
                break;
            case "中信银行":
                itemBankIcon.setImageResource(R.drawable.zxyh);
                break;
            case "中国银行":
                itemBankIcon.setImageResource(R.drawable.zgyh);
                break;
            case "华夏银行":
                itemBankIcon.setImageResource(R.drawable.hxyh);
                break;
            case "浦发银行":
                itemBankIcon.setImageResource(R.drawable.pfyh);
                break;
            case "广发银行":
                itemBankIcon.setImageResource(R.drawable.gfyh);
                break;
            case "兴业银行":
                itemBankIcon.setImageResource(R.drawable.xyyh);
                break;
            case "平安银行":
                itemBankIcon.setImageResource(R.drawable.payh);
                break;
        }

    }

}
