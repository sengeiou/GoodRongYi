package com.lichi.goodrongyi.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.presenter.RegisterPresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.mvp.view.RegisterView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

/**
 * Created by 默小小 on 2017/12/1.
 * 注册
 */

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.tv_regist_change).setOnClickListener(this);
        findViewById(R.id.tv_regist_code).setOnClickListener(this);
        findViewById(R.id. tv_regist_login).setOnClickListener(this);
        findViewById(R.id. btn_regist_reg).setOnClickListener(this);
        findViewById(R.id.iv_regist_back).setOnClickListener(this);

    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist_change:
                mContext.startActivity(new Intent(RegisterActivity.this,ChangePassActivity.class));
                break;
            case R.id.tv_regist_code:
                break;
            case R.id.tv_regist_login:
                mContext. startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                break;
            case R.id.btn_regist_reg:
                break;
            case R.id.iv_regist_back:
                RegisterActivity.this.finish();
                break;
        }
    }
}
