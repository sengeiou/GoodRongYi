package com.lichi.goodrongyi.utill;

import android.text.TextUtils;

/**
 * Created by test on 2017/12/7.
 */

public class PhoneUtil {

    public static boolean isMobileNO(String mobiles) {

        String telRegex = "((1[3,5,8][0-9])|(14[5,7])|(17[0，3,6,7,8])|(19[7]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        else return mobiles.matches(telRegex);
    }

}
