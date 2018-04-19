package com.lichi.goodrongyi.utill;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Auther: Scott
 * Date: 2017/1/20 0020
 * E-mail:hekescott@qq.com
 */

public class SharedPreferenceUtil {
    private static final String spFile = "moto";
    private static final String TOKEN = "token";
    private static final String COMMISSION = "commission"; //佣金
    public static final String FIRST_OPEN = "first_open"; //第一次进入APP

    public static final String FIRST_HOME = "first_home"; //第一次首页引导
    public static final String FIRST_VIDEO = "first_video"; //第一次视频引导
    public static final String FIRST_CREDIT_CARD = "first_credit_card"; //第一次信用卡引导
    public static final String FIRST_ME = "first_me"; //第一次我的界面引导

    private static final String KEY_SP_ISEECAR = "seecarname";
    private static SharedPreferenceUtil inStance;
    private static SharedPreferences mSharedPreferences;

    private SharedPreferenceUtil() {
    }

    private static synchronized SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getApplicationContext().getSharedPreferences(spFile, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return sp.getString(TOKEN, "");
    }

    public static void putToken(Context context, String value) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(TOKEN, value);
        ed.commit();
    }

    public static String getCommission(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return sp.getString(COMMISSION, "");
    }

    public static void putCommission(Context context, String value) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(COMMISSION, value);
        ed.commit();
    }

    public static Boolean getFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        Boolean result = sp.getBoolean(FIRST_OPEN, false);
        return result;
    }

    public static void putFirst(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(FIRST_OPEN, true);
        ed.commit();
    }

    public static boolean getIsJpush(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_SP_ISEECAR, false);
    }

    public static void setIsJpush(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(KEY_SP_ISEECAR, value);
        ed.commit();
    }



    public static Boolean getFirstHome(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        Boolean result = sp.getBoolean(FIRST_HOME, false);
        return result;
    }

    public static void putFirstHome(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(FIRST_HOME, true);
        ed.commit();
    }


    public static Boolean getFirstVideo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        Boolean result = sp.getBoolean(FIRST_VIDEO, false);
        return result;
    }

    public static void putFirstVideo(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(FIRST_VIDEO, true);
        ed.commit();
    }


    public static Boolean getFirstCreditCard(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        Boolean result = sp.getBoolean(FIRST_CREDIT_CARD, false);
        return result;
    }

    public static void putFirstCreditCard(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(FIRST_CREDIT_CARD, true);
        ed.commit();
    }


    public static Boolean getFirstMe(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        Boolean result = sp.getBoolean(FIRST_ME, false);
        return result;
    }

    public static void putFirstMe(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(FIRST_ME, true);
        ed.commit();
    }


}
