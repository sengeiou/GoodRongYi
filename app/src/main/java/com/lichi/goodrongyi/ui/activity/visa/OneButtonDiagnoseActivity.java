package com.lichi.goodrongyi.ui.activity.visa;

import android.os.Bundle;
import android.view.View;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.presenter.OneButtonDiagnosePresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.mvp.view.OneButtonDiagnoseView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

/**
 * 诊断结果
 */
public class OneButtonDiagnoseActivity extends BaseActivity<OneButtonDiagnoseView, OneButtonDiagnosePresenter> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button_diagnose);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public OneButtonDiagnosePresenter initPresenter() {
        return new OneButtonDiagnosePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                OneButtonDiagnoseActivity.this.finish();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
