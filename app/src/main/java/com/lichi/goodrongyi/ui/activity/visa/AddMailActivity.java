package com.lichi.goodrongyi.ui.activity.visa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.AddMailPresenter;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.AddMailView;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

public class AddMailActivity extends BaseActivity<AddMailView, AddMailPresenter> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mail);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.iv_qq_mail).setOnClickListener(this);
        findViewById(R.id.iv_gmail).setOnClickListener(this);
        findViewById(R.id.iv_hotmail).setOnClickListener(this);
    }

    @Override
    public AddMailPresenter initPresenter() {
        return new AddMailPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                AddMailActivity.this.finish();
                break;
            case R.id.iv_qq_mail:
                Toast.makeText(this, "QQ邮箱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_gmail:
                Toast.makeText(this, "GMail", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_hotmail:
                Toast.makeText(this, "HotMail", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}


