package com.lichi.goodrongyi.utill;

import android.content.Context;

import com.lichi.goodrongyi.mvp.model.UserBean;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Auther: Scott
 * Date: 2017/4/20 0020
 * E-mail:hekescott@qq.com
 */

public class JPushUtil {
    public static void setJpushTag(final Context contxt) {

        if (!SharedPreferenceUtil.getIsJpush(contxt)) {
            UserBean account = IOUtils.getUserBean(contxt);
            if (account != null) {
                JPushInterface.setAliasAndTags(contxt, account.id, null,
                        new TagAliasCallback() {
                            @Override
                            public void gotResult(int code, String s, Set<String> set) {
                                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                if (code == 0)
                                    SharedPreferenceUtil.setIsJpush(contxt, true);
                            }
                        });
            }
        }
    }

    public static void clearJpushTag(final Context contxt) {

        if (SharedPreferenceUtil.getIsJpush(contxt)) {
            JPushInterface.setAliasAndTags(contxt, null, null, new TagAliasCallback() {
                @Override
                public void gotResult(int code, String s, Set<String> set) {
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    if (code == 0)
                        SharedPreferenceUtil.setIsJpush(contxt, false);
                }
            });
        }
    }
}
