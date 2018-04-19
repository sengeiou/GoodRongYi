package com.lichi.goodrongyi.ui.activity.login;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.lichi.goodrongyi.utill.SystemUtils;
import com.umeng.socialize.utils.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Auther: Scott
 * Date: 2017/4/20 0020
 * E-mail:hekescott@qq.com
 */

public class LoginSevice extends Service {
    private static final String TAG = "LoginSevice";
    private Context mContext;
    boolean isJpush = false;
    boolean isChat = false;

    private static final int MSG_SET_ALIAS = 1001;
    private final int HANDLER_MSG_JPUSHOUT = 1;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                case HANDLER_MSG_JPUSHOUT:
                    if (isJpush) {
                        LoginSevice.this.stopSelf();
                    }
                    break;
                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e("JPushInterface", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    isJpush = true;
                    SharedPreferenceUtil.setIsJpush(mContext, true);
                    mHandler.sendEmptyMessage(HANDLER_MSG_JPUSHOUT);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e("JPushInterface", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("JPushInterface", logs);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            if (SystemUtils.isEmulator(mContext)) {
                stopSelf();
            } else {
                login(IOUtils.getUserId(mContext), "123456");
                //login("user001", "123");
            }
        } else {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void login(String username, String attached) {
        // SharedPreferenceUtil.putEaseaUserName(mContext, username);
        // SharedPreferenceUtil.putEaseAttached(mContext, attached);
        Logger.d("easechat login username==" + username);
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, IOUtils.getUserId(mContext)));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
