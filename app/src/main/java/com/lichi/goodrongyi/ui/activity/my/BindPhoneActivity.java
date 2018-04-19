package com.lichi.goodrongyi.ui.activity.my;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.presenter.BindPhonePresenter;
import com.lichi.goodrongyi.mvp.view.BindPhoneView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.CountDownTimerUtils;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.PhoneUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity<BindPhoneView, BindPhonePresenter> implements BindPhoneView {

    @BindView(R.id.bind_phone_number)
    EditText phoneNumberEdit;     // 绑定输入手机号码输入框
    @BindView(R.id.bind_phone_code)
    EditText securityCode;        //  绑定输入验证码输入框
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.btn_code)
    TextView btnCode;
    @BindView(R.id.bind_phone_completed)
    Button bindPhoneCompleted;

    Boolean isSend = false;
    String mobile = "";

    @OnClick(R.id.bind_phone_completed)       // 完成按钮点击事件
    public void onCompletedButtonClick() {
        if (isSend) {
            Map<String, String> map = new HashMap<>();
            UserBean userBean = IOUtils.getUserBean(BindPhoneActivity.this);
            map.put("id", userBean.id);
            map.put("mobile", phoneNumberEdit.getText().toString().trim());
            map.put("code", securityCode.getText().toString().trim());
            mPresenter.commitInfo(map);
        } else {
            CommonUtils.showToast(mContext, "请发送验证码");
        }

    }

    @OnClick(R.id.iv_back)            // 返回
    public void back() {
        finish();
    }

    @Override
    public BindPhonePresenter initPresenter() {
        return new BindPhonePresenter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        UserBean userBean = IOUtils.getUserBean(BindPhoneActivity.this);
        if (TextUtils.isEmpty(userBean.mobile)) {
            phoneNumberEdit.setHint("为了账户安全，请绑定手机号");
            btnCode.setVisibility(View.VISIBLE);
            securityCode.setVisibility(View.VISIBLE);
            bindPhoneCompleted.setVisibility(View.VISIBLE);
            //ivEdit.setVisibility(View.GONE);
        } else {
            phoneNumberEdit.setText(userBean.mobile);
            // ivEdit.setVisibility(View.VISIBLE);
        }


    }

    @OnClick({R.id.iv_edit, R.id.btn_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                btnCode.setVisibility(View.VISIBLE);
                securityCode.setVisibility(View.VISIBLE);
                bindPhoneCompleted.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_code:
                mobile = phoneNumberEdit.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(BindPhoneActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!PhoneUtil.isMobileNO(phoneNumberEdit.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                closeKeyboard();
                mPresenter.verificationCode(mobile);
                break;
        }
    }

    @Override
    public void dataCodeSucceed(VerificationCodeBean verificationCodeBean) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btnCode, 60000, 1000, R.drawable.get_code_radius_nor, R.drawable.get_code_radius_y);
        mCountDownTimerUtils.start();
        isSend = true;
    }

    @Override
    public void changeSucceed(String msg) {
        UserBean userBean = IOUtils.getUserBean(mContext);
        userBean.mobile = mobile;
        IOUtils. clearData();
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
        CommonUtils.showToast(this, "完成");
        BindPhoneActivity.this.finish();
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
