package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.BankBean;
import com.lichi.goodrongyi.mvp.presenter.SelectBankPresenter;
import com.lichi.goodrongyi.mvp.view.SelectBankView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

public class SelectBankActivity extends BaseActivity<SelectBankView, SelectBankPresenter> implements View.OnClickListener, SelectBankView {

    private RecyclerView mRlBank;
    private List<BankBean> mData = new ArrayList<>();
    private CommonAdapter<BankBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        initData();
        mRlBank = (RecyclerView) findViewById(R.id.rec_bank_list);
        mRlBank.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<BankBean>(this, R.layout.bank_item, mData) {
            @Override
            public void convert(ViewHolder holder, BankBean item, int position) {
                TextView bankName = holder.getView(R.id.tv_bank_name);
                ImageView itemBankIcon = holder.getView(R.id.iv_bank_icon); //银行图标
                bankName.setText(item.bankname);
                BankIconUtils.setBankIcon(itemBankIcon, item.bankname);
            }
        };
        mRlBank.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, Color.TRANSPARENT));
        mRlBank.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent data = new Intent();
                data.putExtra(Constants.IntentParams.DATA, (Parcelable) mData.get(position));
                setResult(RESULT_OK, data);
                SelectBankActivity.this.finish();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mPresenter.getBankListData();
    }

    @Override
    public SelectBankPresenter initPresenter() {
        return new SelectBankPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                SelectBankActivity.this.finish();
                break;
        }
    }

    private void initData() {
    }

    @Override
    public void dataListSucceed(List<BankBean> bankBean) {
        mData.clear();
        mData.addAll(bankBean);
        mAdapter.notifyDataSetChanged();
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
