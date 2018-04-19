package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;
import com.lichi.goodrongyi.mvp.presenter.MailAddBillPresenter;
import com.lichi.goodrongyi.mvp.view.MailAddBillView;
import com.lichi.goodrongyi.ui.activity.main.MainActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.RecycleViewDivider;
import com.lichi.goodrongyi.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱导入
 */
public class MailAddBillActivity extends BaseActivity<MailAddBillView, MailAddBillPresenter> implements View.OnClickListener, MailAddBillView {
    private static final int LEAD_BACK = 1001;                   //添加成功
    private MyListView mRlMail;
    private List<MaillboxListBean> mData = new ArrayList<>();
    private WrapAdapter<MaillboxListBean> mAdapter;
    private Button accomplish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_add_bill);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_add_mail).setOnClickListener(this);
        findViewById(R.id.accomplish).setOnClickListener(this);

        initData();

        mRlMail = (MyListView) findViewById(R.id.rec_mail_list);
        mAdapter = new WrapAdapter<MaillboxListBean>(this, R.layout.mail_list_item, mData) {
            @Override
            public void convert(ViewHolder holder, MaillboxListBean item, int position) {
                TextView mail_address = holder.getView(R.id.tv_mail_address);
                mail_address.setText(item.email);
            }
        };
        mRlMail.setAdapter(mAdapter);
    }

    @Override
    public MailAddBillPresenter initPresenter() {
        return new MailAddBillPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                MailAddBillActivity.this.finish();
                break;
            case R.id.btn_add_mail:
                if (IOUtils.isLogin(mContext)) {
                    startActivity(new Intent(MailAddBillActivity.this, MailboxActivity.class));
                }
                break;
            case R.id.accomplish:
                if (IOUtils.isLogin(mContext)) {
                    startActivity(new Intent(MailAddBillActivity.this, MainActivity.class));
                }
                break;
        }
    }

    private void initData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LEAD_BACK == requestCode && resultCode == RESULT_OK) {
            mPresenter.getBillDataList(IOUtils.getUserId(mContext));
        }
    }

    @Override
    public void dataListSucceed(List<MaillboxListBean> maillboxListBean) {
        mData.clear();
        mData.addAll(maillboxListBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBillDataList(IOUtils.getUserId(mContext));
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
