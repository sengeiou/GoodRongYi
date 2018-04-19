package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.MailboxListBean;
import com.lichi.goodrongyi.mvp.model.MaillboxListBean;
import com.lichi.goodrongyi.mvp.presenter.MailboxPresenter;
import com.lichi.goodrongyi.mvp.view.MailboxView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2018/1/10.
 * 获取可以导入的邮箱
 */

public class MailboxActivity extends BaseActivity<MailboxView, MailboxPresenter> implements MailboxView {

    private ListView mListview;
    private List<MailboxListBean> mData = new ArrayList<>();
    private WrapAdapter<MailboxListBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        mListview = (ListView) findViewById(R.id.listview);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new WrapAdapter<MailboxListBean>(this, R.layout.mailbox_list_item, mData) {
            @Override
            public void convert(ViewHolder helper, final MailboxListBean item, int position) {
                ImageView icon = helper.getView(R.id.icon);
                TextView name = helper.getView(R.id.name);
                MyPicasso.inject(item.headerUrl, icon);
                name.setText(item.name);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IOUtils.isLogin(mContext)) {
                            Map<String, Serializable> args = new HashMap<>();
                            if (TextUtils.isEmpty(item.suffix)) {
                                args.put(Constants.IntentParams.ID, "");
                            } else {
                                args.put(Constants.IntentParams.ID, item.suffix);
                            }
                            CommonUtils.startNewActivity(MailboxActivity.this, args, AddMailboxActivity.class);
                        }
                    }
                });
            }
        };
        mListview.setAdapter(mAdapter);
        mPresenter.getEmailList();
    }

    @Override
    public MailboxPresenter initPresenter() {
        return new MailboxPresenter();
    }

    @Override
    public void dataListSucceed(List<MailboxListBean> maillboxListBean) {
        mData.clear();
        mData.addAll(maillboxListBean);
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
