package com.lichi.goodrongyi.ui.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.application.MyApplication;
import com.lichi.goodrongyi.mvp.presenter.AccountSecurityPresenter;
import com.lichi.goodrongyi.mvp.view.AccountSecurityView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSecurityActivity extends BaseActivity<AccountSecurityView, AccountSecurityPresenter> {
    @OnClick(R.id.iv_back)      // 绑定返回事件
    public void back() {
        finish();
    }

    @OnClick(R.id.account_security)   // 跳转到手机绑定界面
    public void goToBindPhone() {
        CommonUtils.startNewActivity(this, BindPhoneActivity.class);
    }

    @OnClick(R.id.about_us)     //跳转到关于我们界面
    public void goToAboutUs() {
        CommonUtils.showToast(this, "功能开发中...");
    }

    @OnClick(R.id.security_exit)     //跳转到关于我们界面
    public void goSecurityExit() {
        MyApplication.putValue(Constants.IntentParams.LOGIN_OUT, "123");
        IOUtils.loginOut(mContext, Constants.KEY_ACCOUNT_FILE);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, authListener);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.QQ, authListener);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.SINA, authListener);
        finish();
    }

    @Override
    public AccountSecurityPresenter initPresenter() {
        return new AccountSecurityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        ButterKnife.bind(this);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            showLoadingIndicator(true);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                String temp = "";
                for (String key : data.keySet()) {
                    temp = temp + key + " : " + data.get(key) + "\n";
                }
                Log.e("throw", "结果:" + temp);
            }
            showLoadingIndicator(false);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showLoadingIndicator(false);
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showLoadingIndicator(false);
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
