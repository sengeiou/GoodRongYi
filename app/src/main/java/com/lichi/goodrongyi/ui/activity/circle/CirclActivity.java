package com.lichi.goodrongyi.ui.activity.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.ui.activity.circle.fragment.AllCirclFragment;
import com.lichi.goodrongyi.ui.activity.circle.fragment.HotCirclFragment;
import com.lichi.goodrongyi.mvp.presenter.CirclPresenter;
import com.lichi.goodrongyi.mvp.view.CirclIView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.ui.fragment.LoansFragment;
import com.lichi.goodrongyi.ui.fragment.VisaFragment;
import com.lichi.goodrongyi.utill.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CirclActivity extends BaseActivity<CirclIView, CirclPresenter> implements CirclIView, View.OnClickListener {

    BaseFragment AllCirclFragment, HotCirclFragment, knowFragment;
    @BindView(R.id.alllayout)
    public LinearLayout alllayout;
    @BindView(R.id.allselect)
    public TextView allselect;
    @BindView(R.id.hotlayout)
    public LinearLayout hotlayout;
    @BindView(R.id.hotselect)
    public TextView hotselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circl);
        ButterKnife.bind(this);
        alllayout.setOnClickListener(this);
        hotlayout.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        AllCirclFragmentTab();
    }

    /**
     * 全部圈子
     */
    private void AllCirclFragmentTab() {
        if (AllCirclFragment == null) {
            AllCirclFragment = new AllCirclFragment();
        }
        if (!AllCirclFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, AllCirclFragment).commit();
            // 记录当前Fragment
            knowFragment = AllCirclFragment;
        }
    }

    /**
     * 热门圈子
     */
    private void HotCirclFragmentTab() {
        if (HotCirclFragment == null) {
            HotCirclFragment = new HotCirclFragment();
        }
        if (!HotCirclFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, HotCirclFragment).commit();
            // 记录当前Fragment
            knowFragment = HotCirclFragment;
        }
    }

    public void switchoverAllCirclFragment() {
        AllCirclFragment = new AllCirclFragment();
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), AllCirclFragment);
        knowFragment = AllCirclFragment;
    }

    public void switchoverHotCirclFragment() {
        HotCirclFragment = new HotCirclFragment();
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), HotCirclFragment);
        knowFragment = HotCirclFragment;
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, BaseFragment fragment) {
        if (knowFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(knowFragment).add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(knowFragment).show(fragment).commit();
        }

        knowFragment = fragment;
    }

    @Override
    public CirclPresenter initPresenter() {
        return new CirclPresenter();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alllayout:
                if (!(allselect.getVisibility() == View.VISIBLE)) {
                    switchoverAllCirclFragment();
                    allselect.setVisibility(View.VISIBLE);
                    hotselect.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.hotlayout:
                if (!(hotselect.getVisibility() == View.VISIBLE)) {
                    switchoverHotCirclFragment();
                    hotselect.setVisibility(View.VISIBLE);
                    allselect.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
