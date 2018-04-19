package com.lichi.goodrongyi.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.bean.cheshi;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.presenter.LoginPresenter;
import com.lichi.goodrongyi.mvp.view.LoginView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.CountDownTimerUtils;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.PhoneUtil;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements View.OnClickListener, LoginView {

    TextView mRegistCode; //获取验证码
    EditText mPhone;  //手机号
    EditText mEdittextCode; //验证码
    Button mLoginButton; //登录

    ImageView weixin; //微信
    ImageView weibo; //微博
    ImageView qq; //QQ

    Boolean isSend = false;

    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        mPhone = (EditText) findViewById(R.id.phone);
        mRegistCode = (TextView) findViewById(R.id.tv_regist_code);
        mEdittextCode = (EditText) findViewById(R.id.edittext_code);
        mLoginButton = (Button) findViewById(R.id.loginbutton);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_login_change).setOnClickListener(this);
        findViewById(R.id.tv_login_regist).setOnClickListener(this);

        findViewById(R.id.weixin).setOnClickListener(this);
        findViewById(R.id.weibo).setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);

        mRegistCode.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.weibo:
                //UMShareAPI.get(mContext).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.qq:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.iv_back:
                LoginActivity.this.finish();
                break;
            case R.id.tv_login_change:
                startActivity(new Intent(LoginActivity.this, ChangePassActivity.class));
                break;
            case R.id.tv_login_regist:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_regist_code:
                if (TextUtils.isEmpty(mPhone.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入手机号");
                    return;
                }
                if (!PhoneUtil.isMobileNO(mPhone.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                closeKeyboard();
                mPresenter.verificationCode(mPhone.getText().toString().trim());
                break;
            case R.id.loginbutton:
                if (TextUtils.isEmpty(mPhone.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入手机号");
                    return;
                }
                if (!PhoneUtil.isMobileNO(mPhone.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(mEdittextCode.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入验证码");
                    return;
                }
                closeKeyboard();
                if (isSend) {
                    mPresenter.getLogIn(mPhone.getText().toString().trim(), mEdittextCode.getText().toString().trim());
                } else {
                    CommonUtils.showToast(mContext, "请发送验证码");
                }
                break;
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);
    }

    @Override
    public void showLoadingTasksError() {
        CommonUtils.showToast(mContext, R.string.app_abnormal);

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void dataCodeSucceed(VerificationCodeBean verificationCodeBean) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mRegistCode, 60000, 1000, R.drawable.get_code_radius_nor, R.drawable.get_code_radius_y);
        mCountDownTimerUtils.start();
        isSend = true;
    }


    @Override
    public void dataLoginSucceed(String token) {
        this.token = token;
        mPresenter.getsignUp(token);
    }

    @Override
    public void dataUserBeanucceed(UserBean userBean) {
        SharedPreferenceUtil.putToken(mContext, token);
        userBean.token = token;
        CommonUtils.showToast(mContext, "登录成功");
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(Constants.KEY_ACCOUNT_FILE, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(userBean);
            objectOutputStream.flush();

            IOUtils.clearData();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(objectOutputStream);
            IOUtils.close(outputStream);
        }
        if (!TextUtils.isEmpty(IOUtils.getUserId(mContext))) {
            MobclickAgent.onProfileSignIn(IOUtils.getUserId(mContext));
        }
        startService(new Intent(getContext(), LoginSevice.class));
        finish();
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
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
                if (platform == SHARE_MEDIA.WEIXIN) {
                    mPresenter.getOpenid(data.get("openid"), "weixin", data.get("name"), data.get("iconurl"));
                } else if (platform == SHARE_MEDIA.QQ) {
                    mPresenter.getOpenid(data.get("openid"), "qq", data.get("name"), data.get("iconurl"));
                } else {
                    if (data.get("avatar_hd").equals("http://tvax3.sinaimg.cn/default/images/default_avatar_male_180.gif")){
                        mPresenter.getOpenid(data.get("uid"), "weibo", data.get("name"), "http://ozlfwi1zj.bkt.clouddn.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg?imageView2/1/w/600/h/400/q/75|watermark/2/text/6YeN5bqG6aqK6amw5paH5YyW/font/5b6u6L2v6ZuF6buR/fontsize/480/fill/I0ZGRkZGRg==/dissolve/100/gravity/SouthEast/dx/10/dy/10|imageslim");
                    }else {
                        mPresenter.getOpenid(data.get("uid"), "weibo", data.get("name"), data.get("avatar_hd"));
                    }
                }

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

}
