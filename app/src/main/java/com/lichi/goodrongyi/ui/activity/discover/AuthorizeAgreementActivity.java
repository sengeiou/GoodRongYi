package com.lichi.goodrongyi.ui.activity.discover;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.AuthorizeAgreementPresenter;
import com.lichi.goodrongyi.mvp.view.AuthorizeAgreementView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizeAgreementActivity extends BaseActivity<AuthorizeAgreementView, AuthorizeAgreementPresenter> {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_agreement_content)
    TextView mTvAgreementContent;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_agreement);
        ButterKnife.bind(this);

        initData();
        mTvAgreementContent.setText(content);
    }

    private void initData() {
        String sample = getResources().getString(R.string.teacher_description_sample);
        content = sample;
        for(int i = 0; i < 25; i++) {
            content = content + "\n\n" + sample;
        }
    }

    @Override
    public AuthorizeAgreementPresenter initPresenter() {
        return new AuthorizeAgreementPresenter();
    }


    @OnClick({R.id.iv_back, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                AuthorizeAgreementActivity.this.finish();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
