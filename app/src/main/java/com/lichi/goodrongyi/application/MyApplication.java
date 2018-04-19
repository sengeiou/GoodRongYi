package com.lichi.goodrongyi.application;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.lichi.goodrongyi.interceptor.RequestInterceptor;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.ValueHodler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.util.LinkedList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MyApplication extends MultiDexApplication {
    private static LinkedList<Activity> activityStack = new LinkedList<>();
    private static final ValueHodler sGlobalValues = new ValueHodler();
    public static MyApplication mMyApplication = null;
    public MyPicasso myPicasso;

    public static synchronized MyApplication getInstance() {
        if (mMyApplication == null) {
            mMyApplication = new MyApplication();
        }
        return mMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        UMConfigure.init(this, null, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL); //设置友盟统计
        MyPicasso.init(getApplicationContext()); //加载图片初始化
        myPicasso = new MyPicasso(getApplicationContext());
        RequestInterceptor.appContext = getApplicationContext();

        //极光推送
        JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        //当程序发生Uncaught异常的时候,由该类来接管程序,一定要在这里初始化
       // CrashHandler.getInstance().init(this);

        //APP全局日志Log初始化
        Logger.init("Exhibition") // default PRETTYLOGGER or use just init()
                .methodCount(3) // default 2
                .hideThreadInfo() // default shown
                //.logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx4ead3d933116221b", "2d9fa22e6d7955a709d05f94887af121");
        PlatformConfig.setSinaWeibo("1938930734", "9b65d175d91d5f1ad15a15cf94942b01", "http://sns.whalecloud.com/");
        PlatformConfig.setQQZone("1106545023", "KtU73Rf5F2vV0X0d");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void removeValue(String key) {
        sGlobalValues.remove(key);
    }

    public static <T> void putValue(String key, T value) {
        sGlobalValues.putValue(key, value);
    }

    public static <T> T getValue(String key) {
        return sGlobalValues.getValue(key);
    }
}
