package com.lichi.goodrongyi.ui.activity.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.CourseSignUpBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.presenter.SignUpCoursePresenter;
import com.lichi.goodrongyi.mvp.view.SignUpCourseView;
import com.lichi.goodrongyi.ui.activity.login.LoginSevice;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.CountDownTimerUtils;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.PhoneUtil;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 报名课程
 */
public class SignUpCourseActivity extends BaseActivity<SignUpCourseView, SignUpCoursePresenter> implements View.OnClickListener, SignUpCourseView {

    private TextView mTVOriginalPrice;
    private TextView mTVCourseTitle;
    private TextView mTVChangePhone;

    private EditText mETUserName;
    private EditText mETNote;
    private EditText mEtVerifyCode;

    private LinearLayout mApplylayout; //验证码布局
    private TextView mTvUserType;
    private TextView mTvPrice;
    private TextView mPhoneNumber; //本机号码
    private EditText mTvPhoneNumber; //手机号
    private TextView mTvChangePhone; //切换
    private TextView mBtnSendVerifyCode; //验证码
    public String ID;
    public String money;
    public String title;
    public boolean isSwitchover = true;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_course);
        ID = getIntent().getStringExtra(Constants.IntentParams.ID);
        money = getIntent().getStringExtra(Constants.IntentParams.ID2);
        title = getIntent().getStringExtra(Constants.IntentParams.ID3);
        initView();
        if (TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            mApplylayout.setVisibility(View.VISIBLE);
            mTvChangePhone.setVisibility(View.GONE);
            mTvPhoneNumber.setVisibility(View.VISIBLE);
            mPhoneNumber.setVisibility(View.GONE);
        } else {
            mApplylayout.setVisibility(View.GONE);
            mTvChangePhone.setVisibility(View.VISIBLE);
            UserBean userBean = IOUtils.getUserBean(mContext);
            if (userBean != null) {
                mETUserName.setText(userBean.nickname);
                if (!TextUtils.isEmpty(userBean.mobile)) {
                    if (!TextUtils.isEmpty(userBean.mobile)) {
                        mPhoneNumber.setText(userBean.mobile);
                    } else {
                        mTvPhoneNumber.setText("");
                        mApplylayout.setVisibility(View.VISIBLE);
                        mTvChangePhone.setVisibility(View.GONE);
                    }
                } else {
                    mApplylayout.setVisibility(View.VISIBLE);
                    mTvChangePhone.setVisibility(View.GONE);
                    mTvPhoneNumber.setVisibility(View.VISIBLE);
                    mPhoneNumber.setVisibility(View.GONE);
                }
            }
        }
        mTvPrice.setText("￥ " + money);
        mTVCourseTitle.setText("" + title);

    }

    public void initView() {
        mBtnSendVerifyCode = (TextView) findViewById(R.id.btn_send_verify_code);
        mTvChangePhone = (TextView) findViewById(R.id.tv_change_phone);
        mEtVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvPhoneNumber = (EditText) findViewById(R.id.tv_phone_number);
        mApplylayout = (LinearLayout) findViewById(R.id.applylayout);
        mTvUserType = (TextView) findViewById(R.id.tv_user_type);
        mTVCourseTitle = (TextView) findViewById(R.id.tv_course_title);
        mETUserName = (EditText) findViewById(R.id.et_user_name);
        mETNote = (EditText) findViewById(R.id.et_note);


        mTVOriginalPrice = (TextView) findViewById(R.id.tv_original_price);
        mTVOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  // 给原价添加中划线

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        mTvChangePhone.setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        mBtnSendVerifyCode.setOnClickListener(this);
    }

    @Override
    public SignUpCoursePresenter initPresenter() {
        return new SignUpCoursePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                SignUpCourseActivity.this.finish();
                break;
            case R.id.tv_change_phone:
                mApplylayout.setVisibility(View.VISIBLE);
                mTvPhoneNumber.setText("");
                mTvPhoneNumber.setVisibility(View.VISIBLE);
                mPhoneNumber.setVisibility(View.GONE);
                isSwitchover = false;
                break;
            case R.id.btn_send_verify_code:
                if (TextUtils.isEmpty(mTvPhoneNumber.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入手机号");
                    return;
                }
                if (!PhoneUtil.isMobileNO(mTvPhoneNumber.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                closeKeyboard();
                mPresenter.verificationCode(mTvPhoneNumber.getText().toString().trim());
                break;
            case R.id.btn_signup:
                UserBean userBean = IOUtils.getUserBean(mContext);
                if (TextUtils.isEmpty(IOUtils.getToken(mContext))) { //没登陆
                    if (TextUtils.isEmpty(mETUserName.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "用户名不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(mTvPhoneNumber.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "电话不能为空");
                        return;
                    }
                    if (!PhoneUtil.isMobileNO(mTvPhoneNumber.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "请输入正确的手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(mEtVerifyCode.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "验证码不能为空");
                        return;
                    }
                    mPresenter.getSignUp(builderParams1());
                } else if (TextUtils.isEmpty(userBean.mobile)) { //第三方
                    if (TextUtils.isEmpty(mETUserName.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "用户名不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(mTvPhoneNumber.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "电话不能为空");
                        return;
                    }
                    if (!PhoneUtil.isMobileNO(mTvPhoneNumber.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "请输入正确的手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(mEtVerifyCode.getText().toString().trim())) {
                        CommonUtils.showToast(mContext, "验证码不能为空");
                        return;
                    }
                    mPresenter.getSignUp(builderParams2());
                } else {
                    if (isSwitchover) { //没有切换
                        if (TextUtils.isEmpty(mETUserName.getText().toString().trim())) {
                            CommonUtils.showToast(mContext, "用户名不能为空");
                            return;
                        }
                        mPresenter.getSignUp(builderParams());
                    } else { //以切换
                        if (TextUtils.isEmpty(mETUserName.getText().toString().trim())) {
                            CommonUtils.showToast(mContext, "用户名不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(mTvPhoneNumber.getText().toString().trim())) {
                            CommonUtils.showToast(mContext, "电话不能为空");
                            return;
                        }
                        if (!PhoneUtil.isMobileNO(mTvPhoneNumber.getText().toString().trim())) {
                            CommonUtils.showToast(mContext, "请输入正确的手机号");
                            return;
                        }
                        if (TextUtils.isEmpty(mEtVerifyCode.getText().toString().trim())) {
                            CommonUtils.showToast(mContext, "验证码不能为空");
                            return;
                        }
                        mPresenter.getSignUp(builderParams2());
                    }

                }

                break;
        }
    }

    /**
     * 读取数据
     *
     * @return
     */
    private Map<String, String> builderParams() {
        UserBean userBean = IOUtils.getUserBean(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", ID);
        params.put("userId", userBean.id);
        params.put("nickname", mETUserName.getText().toString().trim());
        params.put("mobile", userBean.mobile);
        params.put("remark", mETNote.getText().toString().trim());
        params.put("code", "");
        return params;
    }

    /**
     * 修改了号码数据
     *
     * @return
     */
    private Map<String, String> builderParams2() {
        UserBean userBean = IOUtils.getUserBean(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", ID);
        params.put("userId", userBean.id);
        params.put("nickname", mETUserName.getText().toString().trim());
        params.put("mobile", mTvPhoneNumber.getText().toString().trim());
        params.put("remark", mETNote.getText().toString().trim());
        params.put("code", mEtVerifyCode.getText().toString().trim());
        return params;
    }

    /**
     * 没登陆数据
     *
     * @return
     */
    private Map<String, String> builderParams1() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", ID);
        params.put("userId", "");
        params.put("nickname", mETUserName.getText().toString().trim());
        params.put("mobile", mTvPhoneNumber.getText().toString().trim());
        params.put("remark", mETNote.getText().toString().trim());
        params.put("code", mEtVerifyCode.getText().toString().trim());
        return params;
    }

    @Override
    public void dataListSucceed(CourseSignUpBean courseSignUpBean) {
        if (TextUtils.isEmpty(courseSignUpBean.token)) {
            // finish();
            token = IOUtils.getToken(mContext);
            mPresenter.getUser();
        } else {
            token = courseSignUpBean.token;
            mPresenter.getUser(token);
        }

    }

    @Override
    public void dataCodeSucceed(VerificationCodeBean verificationCodeBean) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mBtnSendVerifyCode, 60000, 1000, R.color.line, R.color.colorPrimary);
        mCountDownTimerUtils.start();
    }

    @Override
    public void dataUserBeanucceed(UserBean userBean) {
        userBean.token = token;
        IOUtils.clearData();
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
        Intent mIntent = new Intent();
        this.setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
