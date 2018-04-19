package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.presenter.AddMailboxPresenter;
import com.lichi.goodrongyi.mvp.view.AddMailboxView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;

/**
 * Created by test on 2017/12/14.
 * 添加邮箱
 */

public class AddMailboxActivity extends BaseActivity<AddMailboxView, AddMailboxPresenter> implements View.OnClickListener, AddMailboxView {

    LinearLayout lyBack; //返回
    EditText mailbox; //邮箱
    EditText password; //密码
    Button btnAddMailboxSave; //邮箱
    TextView mSuffix;//后缀
    TextView passwordType;//类型

    public String suffixString = "";

    @Override
    public AddMailboxPresenter initPresenter() {
        return new AddMailboxPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmailbox);
        suffixString = getIntent().getStringExtra(Constants.IntentParams.ID);
        passwordType = (TextView) findViewById(R.id.passwordtype);
        lyBack = (LinearLayout) findViewById(R.id.ly_back);
        mailbox = (EditText) findViewById(R.id.mailbox);
        password = (EditText) findViewById(R.id.password);
        btnAddMailboxSave = (Button) findViewById(R.id.btn_add_mailbox_save);
        mSuffix = (TextView) findViewById(R.id.suffix);
        lyBack.setOnClickListener(this);
        btnAddMailboxSave.setOnClickListener(this);
        if (!TextUtils.isEmpty(suffixString)) {
            mSuffix.setVisibility(View.VISIBLE);
            mSuffix.setText(suffixString);
            if (suffixString.equals("@qq.com")||suffixString.equals("@163.com")||suffixString.equals("@126.com")) {
                passwordType.setText("授权码 ：");
                password.setHint("请输入授权码");
            } else {
                passwordType.setText("密码 ：");
                password.setHint("请输入密码");
            }
        } else {
            mSuffix.setVisibility(View.GONE);
            mSuffix.setText("");
            passwordType.setText("密码 ：");
            password.setHint("请输入密码");
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_back:
                finish();
                break;
            case R.id.btn_add_mailbox_save:
                String mailboxString = mailbox.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                if (TextUtils.isEmpty(mailboxString)) {
                    CommonUtils.showToast(mContext, "请输入邮箱");
                    return;
                }
                if (TextUtils.isEmpty(passwordString)) {
                    CommonUtils.showToast(mContext, "请输入授权码");
                    return;
                }
                String userId = IOUtils.getUserId(mContext);
                mPresenter.getBillData(userId, mailboxString + suffixString, passwordString);
                break;
        }
    }

    @Override
    public void dataSucceed(AddMaillboxBean addMaillboxBean) {
/*        Intent data = new Intent();
        setResult(RESULT_OK, data);*/
        finish();
        CommonUtils.startNewActivity(mContext, MailAddBillActivity.class);

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
}
