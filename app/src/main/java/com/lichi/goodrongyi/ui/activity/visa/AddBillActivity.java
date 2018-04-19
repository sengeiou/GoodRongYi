package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.AddBillPresenter;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.AddBillView;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

/**
 * 添加账单
 */

public class AddBillActivity extends BaseActivity<AddBillView, AddBillPresenter> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.ll_manual_import).setOnClickListener(this);
        findViewById(R.id.ll_mail_import).setOnClickListener(this);
    }

    @Override
    public AddBillPresenter initPresenter() {
        return new AddBillPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                AddBillActivity.this.finish();
                break;
            case R.id.ll_mail_import:
                startActivity(new Intent(AddBillActivity.this, MailboxActivity.class));
              //  startActivity(new Intent(AddBillActivity.this, MailAddBillActivity.class));
                break;
            case R.id.ll_manual_import:
               // startActivity(new Intent(AddBillActivity.this, ManuallyAddBillActivity.class));
                startActivity(new Intent(AddBillActivity.this, ImportCreditCardActivity.class));
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
