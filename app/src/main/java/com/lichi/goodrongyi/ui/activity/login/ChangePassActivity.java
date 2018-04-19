package com.lichi.goodrongyi.ui.activity.login;

import android.os.Bundle;
import android.view.View;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.ChangePassPresenter;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.ChangePassView;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

/**
 * 修改密码
 */
public class ChangePassActivity extends BaseActivity<ChangePassView, ChangePassPresenter> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id. btn_chang_commit).setOnClickListener(this);
        findViewById(R.id.btn_change_code).setOnClickListener(this);

    }

    @Override
    public ChangePassPresenter initPresenter() {
        return new ChangePassPresenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ChangePassActivity.this.finish();
                break;
            case R.id.btn_chang_commit:
                break;
            case R.id.btn_change_code:
                break;
        }
    }
}
