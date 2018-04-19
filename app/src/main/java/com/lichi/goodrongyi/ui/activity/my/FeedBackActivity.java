package com.lichi.goodrongyi.ui.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.FeedBackPresenter;
import com.lichi.goodrongyi.mvp.view.FeedBackView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity<FeedBackView,FeedBackPresenter> {
    @BindView(R.id.feedback_information)
    EditText mFeedbackInformation;          // 绑定意见的输入框

    @BindView(R.id.feedback_phone_number)
    EditText mPhoneNumber;                   // 绑定输入手机号码的输入框

    @OnClick(R.id.feedback_commit)
    public void onCommitButtonClick() {        // 提交按钮点击事件
        CommonUtils.showToast(this,"提交");
    }

    @OnClick(R.id.iv_back)                    //点击返回键的事件
    public void onBackClick() {
         finish();
    }

    @Override
    public FeedBackPresenter initPresenter() {
        return  new FeedBackPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
