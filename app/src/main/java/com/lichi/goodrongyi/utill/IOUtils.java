package com.lichi.goodrongyi.utill;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.ui.activity.login.LoginActivity;
import com.lichi.goodrongyi.ui.activity.login.LoginOutSevice;
import com.umeng.analytics.MobclickAgent;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Auther: Scott
 * Date: 2017/1/6 0006
 * E-mail:hekescott@qq.com
 */

public class IOUtils {
    private static UserBean mUserBean = null;
    private static String mToken = "";
    private static String mUserId = "";

    public synchronized static <T> T getObject(Context context, String fileName) {
        T t1 = null;
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            t1 = (T) objectInputStream.readObject();

        } catch (Exception e) {
            Logger.e("IOUtils【反序列化对象失败:】" + e.getMessage());
        } finally {
            close(fileInputStream);
            close(objectInputStream);
        }
        return t1;
    }


    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.e(e.getLocalizedMessage());
            }
        }
    }

    public static synchronized String getToken(Context context) {
        String token = SharedPreferenceUtil.getToken(context);
        UserBean userBean = getUserBean(context);
        if (TextUtils.isEmpty(mToken)) {
            if (userBean != null) {
                mToken = userBean.token;
            }
        }
        return mToken;
    }

    /**
     * 是否登录
     *
     * @param context
     * @return false 未登录 true 登录
     */
    public static synchronized boolean isLogin(Context context) {

        if (TextUtils.isEmpty(getToken(context))) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }
        return true;
    }

    public static synchronized String getUserId(Context context) {

        UserBean a = getUserBean(context);
        if (TextUtils.isEmpty(mUserId) || "0".equals(mUserId)) {
            if (a != null) {
                mUserId = a.id + "";
                //mUserId = "389722919837630464";
            }
        }

        return mUserId;
    }

    public static synchronized UserBean getUserBean(Context context) {

        if (mUserBean == null) {
            mUserBean = getObject(context, Constants.KEY_ACCOUNT_FILE);
        }
        return mUserBean;
    }

    public static <T> void writeObject(Context context, T t, String name) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(t);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        } finally {
            close(objectOutputStream);
            close(fileOutputStream);
        }

    }

    public static boolean deleteFile(Context context, String name) {

        try {
            File f = context.getFileStreamPath(name);
            if (f.isFile()) {
                return f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean loginOut(Context context, String name) {
        clearData();
        boolean isOut;
        if (deleteFile(context, name)) {
            //  EMClient.getInstance().logout(true);//退出环信登录
            context.startService(new Intent(context, LoginOutSevice.class));
            //SharedPreferenceUtil.setIsJpush(context, false);
            MobclickAgent.onProfileSignOff(); //友盟统计推出
            SharedPreferenceUtil.putToken(context, "");
            isOut = true;
            Logger.d("login out" + isOut);
        } else {
            isOut = false;
        }

        return isOut;
    }

    public static void clearData() {

        mUserBean = null;
        mToken = "";
        mUserId = "";

    }

}
